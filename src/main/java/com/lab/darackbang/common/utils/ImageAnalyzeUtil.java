package com.lab.darackbang.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.darackbang.entity.ImageAnalyze;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 이미지 분석 유틸리티 클래스
 * 주어진 이미지를 분석 서버에 전송하고, 분석 결과를 바탕으로 파일을 저장하는 기능을 제공.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ImageAnalyzeUtil {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${com.lab.upload.path}")
    private String uploadPath;

    /**
     * 이미지 분석 및 저장 함수
     * 일반 이미지 파일 및 설명 파일을 분석하여 그 결과를 저장하고 ImageAnalyze 객체 리스트로 반환합니다.
     *
     * @param files     일반 이미지 파일 리스트
     * @param descFiles 설명 이미지 파일 리스트
     * @return 이미지 분석 결과를 포함한 ImageAnalyze 객체 리스트
     */
    public List<ImageAnalyze> imageAnalyze(List<MultipartFile> files, List<MultipartFile> descFiles) {
        return Stream.of(
                        Optional.ofNullable(files).orElse(Collections.emptyList()),
                        Optional.ofNullable(descFiles).orElse(Collections.emptyList())
                )
                .flatMap(Collection::stream)//두개의 값 합치기
                .filter(file -> file != null && !file.isEmpty()) // 빈 파일과 null 값 필터링
                .map(this::processAndSaveImage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    /**
     * 개별 이미지 파일을 분석 서버로 전송하고, 결과를 저장하는 함수
     *
     * @param file 처리할 이미지 파일
     * @return 이미지 분석 결과를 포함한 ImageAnalyze 객체 또는 처리 실패 시 null
     */
    private ImageAnalyze processAndSaveImage(MultipartFile file) {
        try {

            String imageFileName = UUID.randomUUID() + "_" + Objects.requireNonNull(file.getOriginalFilename());

            // 서버로 파일을 전송하여 분석 결과를 가져옴
            String result = sendFileToServer(file, imageFileName);

            if (Optional.ofNullable(result).filter(r -> !r.isEmpty()).isPresent()) {
                // 분석 결과에서 이미지를 디코딩하여 저장
                byte[] imageBytes = decodeImage(result);
                String finalImageFileName = imageFileName.replaceFirst("[.][^.]+$", "") + ".jpg";
                Path savedFilePath = saveImageToFileSystem(imageBytes, finalImageFileName);

                // ImageAnalyze 객체 생성
                ImageAnalyze imageAnalyze = new ImageAnalyze();
                imageAnalyze.setFileName(finalImageFileName);

                log.info("이미지 파일 저장 성공: {}", savedFilePath);

                return imageAnalyze;
            }

            return null;
        } catch (IOException e) {
            log.error("Error processing image:{} ", e.getMessage());
            return null;
        }
    }

    /**
     * 분석 서버로 파일을 전송하는 함수
     *
     * @param file          전송할 이미지 파일
     * @param imageFileName 서버에 전송될 파일 이름
     * @return 서버에서 반환된 분석 결과 문자열
     * @throws IOException 파일 전송 중 발생한 예외
     */
    private String sendFileToServer(MultipartFile file, String imageFileName) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("message", imageFileName);
            builder.part("image", file.getResource());

            return webClient.post()
                    .uri("/detect")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(e -> {
                        // 접속 오류가 발생할 경우 로그 기록
                        log.error("Error connecting to server for file {}: {}", imageFileName, e.getMessage());
                        return Mono.empty();  // 빈 Mono 반환
                    })
                    .block();
        } catch (Exception e) {
            // 다른 예외 발생 시 처리
            log.error("Unexpected error during file processing: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 서버에서 받은 분석 결과 문자열을 바탕으로 이미지를 디코딩하는 함수
     *
     * @param result 서버로부터 수신한 분석 결과 문자열
     * @return 디코딩된 이미지 데이터 (byte 배열)
     * @throws IOException 결과 문자열 파싱 또는 디코딩 중 발생한 예외
     */
    private byte[] decodeImage(String result) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(result);
        String base64Image = jsonNode.get("image").asText();
        return Base64.getDecoder().decode(base64Image);
    }

    /**
     * 디코딩된 이미지 데이터를 파일 시스템에 저장하는 함수
     *
     * @param imageBytes    디코딩된 이미지 데이터 (byte 배열)
     * @param imageFileName 저장할 파일의 이름
     * @return 저장된 파일의 경로
     * @throws IOException 파일 저장 중 발생한 예외
     */
    private Path saveImageToFileSystem(byte[] imageBytes, String imageFileName) throws IOException {
        Path analyzeDir = Paths.get(uploadPath, "analyze");
        Files.createDirectories(analyzeDir);
        Path filePath = analyzeDir.resolve(imageFileName);
        Files.write(filePath, imageBytes, StandardOpenOption.CREATE);
        return filePath;
    }
    /**
     * 업로드 이미지 가져 뷰에서 보기
     * @param fileName
     * @return
     */
    public ResponseEntity<Resource> getFile(String fileName){

        Resource resource = new FileSystemResource(uploadPath+File.separator+"analyze"+ File.separator+fileName);

        if(!resource.exists()){
            resource = new FileSystemResource(uploadPath+File.separator+"default.png");
        }

        HttpHeaders httpHeaders = new HttpHeaders();

        try{
            httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }
}
