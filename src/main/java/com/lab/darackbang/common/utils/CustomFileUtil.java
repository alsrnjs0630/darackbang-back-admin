package com.lab.darackbang.common.utils;

import com.lab.darackbang.dto.common.FileInfoDTO;
import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${com.lab.upload.path}")
    private String uploadPath;

    @Value("${com.lab.eventUpload.path}")
    private String eventUploadPath;

    /**
     * 프로젝트 실행시 무조건 실행
     */
    @PostConstruct
    public void init() throws IOException {

        Path tempFolderPath = Paths.get(uploadPath);
        // 디렉토리가 존재하지 않으면 생성하고, 이미 존재하면 아무 작업도 하지 않음
        Files.createDirectories(tempFolderPath);
        // 절대 경로로 변환
        uploadPath = tempFolderPath.toAbsolutePath().toString();

        log.info("******파일 업로드 경로:{}", uploadPath);

        Path eventFolderPath = Paths.get(eventUploadPath);
        // 디렉토리가 존재하지 않으면 생성하고, 이미 존재하면 아무 작업도 하지 않음
        Files.createDirectories(eventFolderPath);
        // 절대 경로로 변환
        eventUploadPath = eventFolderPath.toAbsolutePath().toString();

        log.info("******이벤트 파일 업로드 경로:{}", eventUploadPath);

    }

    /**
     * 이미지 파일 저장및 썸네일 이미지 생성
     *
     * @param files
     * @return 이미지 파일명 리스트 리턴
     * @throws IOException
     */
    public List<String> saveProductDescFiles(List<MultipartFile> files) throws IOException {

        if (files == null || files.isEmpty()) {
            return null;
        }

        List<String> uploadNames = new ArrayList<>();

        files.forEach(uploadFile -> {
            String savedName = UUID.randomUUID().toString() + "_" + uploadFile.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, savedName);
            try {
                Files.copy(uploadFile.getInputStream(), savePath);
                uploadNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        return uploadNames;
    }

    /**
     * 이미지 파일 저장및 썸네일 이미지 생성
     *
     * @param files
     * @return 이미지 파일명 리스트 리턴
     * @throws IOException
     */
    public List<String> saveProductInfoFiles(List<MultipartFile> files) throws IOException {

        if (files == null || files.isEmpty()) {
            return null;
        }

        List<String> uploadNames = new ArrayList<>();

        files.forEach(uploadFile -> {
            String savedName = UUID.randomUUID().toString() + "_" + uploadFile.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, savedName);
            try {
                Files.copy(uploadFile.getInputStream(), savePath);

                //썸네일 생성
                String contentType = uploadFile.getContentType();
                if (contentType != null && contentType.startsWith("image")) {
                    Path thumbPath = Paths.get(uploadPath, "thumbnail_" + savedName);
                    Thumbnails.of(savePath.toFile()).size(400, 400).toFile(thumbPath.toFile());
                }
                uploadNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        return uploadNames;
    }

    /**
     * 업로드 이미지 가져 뷰에서 보기
     *
     * @param fileName
     * @return
     */
    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if (!resource.exists()) {
            resource = new FileSystemResource(uploadPath + File.separator + "default.png");
        }

        HttpHeaders httpHeaders = new HttpHeaders();

        try {
            httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

    /**
     * 사용자가 삭제한 파일 리스트 물리적으로 삭제(원본이미지, 썸네일 이미지)
     *
     * @param fileNames
     */
    public void deleteFile(List<String> fileNames) {

        if (fileNames == null || fileNames.isEmpty()) {
            return;
        }

        fileNames.forEach(fileName -> {

            String thumbNailFileName = "s_" + fileName;
            Path thumbNailPath = Paths.get(uploadPath, thumbNailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbNailPath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    /**
     * 이미지 파일 저장및 썸네일 이미지 생성
     *
     * @param files
     * @return 이미지 파일명 리스트 리턴
     * @throws IOException
     */
    public List<FileInfoDTO> updateNewProductDescFiles(List<MultipartFile> files) throws IOException {

        if (files == null || files.isEmpty()) {
            return null;
        }

        List<FileInfoDTO> uploadFileInfo = new ArrayList<>();

        files.forEach(uploadFile -> {

            FileInfoDTO fileInfoDTO = parseFileName(uploadFile.getOriginalFilename(), "DESC");

            String savedName = UUID.randomUUID().toString() + "_" + fileInfoDTO.getFileName();

            fileInfoDTO.setFileName(savedName);

            Path savePath = Paths.get(uploadPath, savedName);
            try {
                Files.copy(uploadFile.getInputStream(), savePath);
                uploadFileInfo.add(fileInfoDTO);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        return uploadFileInfo;
    }

    /**
     * 이미지 파일 저장및 썸네일 이미지 생성
     *
     * @param files
     * @return 이미지 파일명 리스트 리턴
     * @throws IOException
     */
    public List<FileInfoDTO> updateNewProductInfoFiles(List<MultipartFile> files) throws IOException {

        if (files == null || files.isEmpty()) {
            return null;
        }

        List<FileInfoDTO> uploadFileInfo = new ArrayList<>();

        files.forEach(uploadFile -> {

            FileInfoDTO fileInfoDTO = parseFileName(uploadFile.getOriginalFilename(), "INFO");

            String savedName = UUID.randomUUID().toString() + "_" + fileInfoDTO.getFileName();

            fileInfoDTO.setFileName(savedName);

            Path savePath = Paths.get(uploadPath, savedName);
            try {
                Files.copy(uploadFile.getInputStream(), savePath);

                //썸네일 생성
                String contentType = uploadFile.getContentType();
                if (contentType != null && contentType.startsWith("image")) {
                    Path thumbPath = Paths.get(uploadPath, "thumbnail_" + savedName);
                    Thumbnails.of(savePath.toFile()).size(400, 400).toFile(thumbPath.toFile());
                }
                uploadFileInfo.add(fileInfoDTO);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        return uploadFileInfo;
    }


    private FileInfoDTO parseFileName(String fullName, String type) {
        return new FileInfoDTO(type, Integer.parseInt(extractSequenceNumber(fullName)), extractFileName(fullName));
    }

    /**
     * 이미지파일명 앞에 있는 시퀀스 번호 추출
     *
     * @param fileName
     * @return
     */
    private String extractSequenceNumber(String fileName) {
        // 정규표현식: 파일명의 시작에서 숫자와 언더스코어(_)로 시작하는 패턴을 찾음
        String regex = "^(\\d+)_";  // 파일명의 시작(^)에서 숫자(\\d+)와 언더스코어(_)를 포함한 패턴

        // Pattern과 Matcher를 사용하여 파일명에서 일치하는 패턴을 찾음
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);

        // 패턴이 파일명에서 일치하면 숫자 부분을 반환
        if (matcher.find()) {
            return matcher.group(1);  // 첫 번째 캡처 그룹 (숫자)만 반환
        } else {
            return "1";  // 패턴이 없을 경우 null 반환
        }
    }

    private String extractFileName(String fileName) {
        // 언더스코어(_)의 인덱스를 찾아서 그 이후의 부분을 추출
        int underscoreIndex = fileName.indexOf('_');

        if (underscoreIndex != -1) {
            // 언더스코어 뒤의 파일명을 반환
            return fileName.substring(underscoreIndex + 1);
        } else {
            return "default"; // 언더스코어가 없을 경우 null 반환
        }
    }

    /**
     * 이벤트 이미지 파일 저장및 썸네일 이미지 생성
     *
     * @param file
     * @return 이벤트 이미지 파일명 리턴
     * @throws IOException
     */
    public String saveEventFile(MultipartFile file) throws IOException {

        if (file == null) {
            return null;
        }

        String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path savePath = Paths.get(eventUploadPath, savedName);
        try {
            Files.copy(file.getInputStream(), savePath);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return savedName;
    }

    /**
     * 이벤트 이미지 가져 뷰에서 보기
     *
     * @param fileName
     * @return
     */
    public ResponseEntity<Resource> getEventFile(String fileName) {
        Resource resource = new FileSystemResource(eventUploadPath + File.separator + fileName);

        if (!resource.exists()) {
            resource = new FileSystemResource(eventUploadPath + File.separator + "default.png");
        }

        HttpHeaders httpHeaders = new HttpHeaders();

        try {
            httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

}
