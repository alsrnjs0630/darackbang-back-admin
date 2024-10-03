package com.lab.darackbang.common.utils;

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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageUtil {
    // application.yml에 설정된 값을 변수 uploadPath에 대입
    @Value("${com.lab.upload.path}")
    private String uploadPath;

    /*
    # upload 디렉토리만 자동으로 생성하는 메서드
    @PostConstruct: 파라미터가 없는 메서드에만 사용 가능
    uploadPath에 값이 대입된 후에 아래 로직 실행
    */
    @PostConstruct
    public void init() {
        createDirectory(uploadPath, "");
    }

    // # 디렉토리 생성 메서드
    private Path createDirectory(String uploadPath, String path) {
        /*
        Paths.get(): Paths 클래스에서 제공하는 파일 시스템 경로 생성 메서드
        (1) 주로 문자열을 매개변수로 받아서 Path 타입 객체 생성
        (2) 여러 개의 문자열이 매개변수일 경우, 문자열 사이에 적절한 경로 구분자(\)를 삽입해 하나의 경로로 만들어줌
        */
        Path addUploadPath = (path == null || path.isEmpty())
                // 변수 path 값이 없는 경우 uploadPath의 경로만 생성
                ? Paths.get(uploadPath)
                // uploadPath, 경로 구분자, path를 합친 하나의 경로 생성
                : Paths.get(uploadPath, path);

        try {
            /*
            createDirectories(): Files 클래스에서 제공하는 디렉토리 생성 메서드
            (1) 매개변수로 받는 Path 타입의 객체가 가리키는 경로에 디렉토리 생성
            (2) 필요한 경우 부모 디렉토리도 함께 생성
            (3) 이미 생성하려는 디렉토리가 존재하는 경우 에러 발생 X
            (4) IOException으로 예외 처리를 명확하게 가능
            cf.) createDirectory()는 단일 디렉토리만 생성한다는 점에서 차이가 있음
            */
            Files.createDirectories(addUploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        toAbsolutePath(): 변수 addUploadPath가 가지고 있는 경로를 절대 경로로 변환
        절대 경로: ex) C:\\uploads. 다른 폴더가 기준이 아님
        */
        return addUploadPath.toAbsolutePath();
    }

    /*
    # 업로드할 이미지들의 파일명을 만들고 추가한 경로에 저장하는 메서드
    -> product 경로에 업로드하는 경우. 섬네일 생성 기능 추가
    (1) 이때, 이 파일명들은 리스트에 담아져있음
    (2) 반환하는 것이 이미지 파일명일 뿐 이 메서드 안에서는 파일명도 생성하고 서버에 파일도 저장함
    (3) path: uploadPath 뒤에 추가하는 경로명 = upload 폴더 안에 추가하는 폴더명
    (4) List<MultipartFile> images: 업로드할 이미지들을 담는 리스트를 변수 images로 선언
    */
    public List<String> uploadImages(String path, List<MultipartFile> images, String pno) {
        /*
        업로드할 이미지 리스트가 null이거나 비어있는지 체크하는 유효성 검사
        images == null: 리스트 images가 초기화되지 않음. 이 변수 사용시 NullPointerException 발생
        images.isEmpty(): 리스트 images가 초기화는 됐지만 비어있음
        둘 중 하나일 경우 null값 반환
        */
        if (images == null || images.isEmpty()) {
            return null;
        }

        /*
        createDirectory(uploadPath, path)
        (1) 추가한 경로(변수 path)에 해당하는 디렉토리 생성
        (2) Path 객체 타입인 addUploadPath에 uploadPath와 path를 합친 하나의 절대 경로 대입
        */
        Path addUploadPath = createDirectory(uploadPath, path);

        // 업로드할 이미지들의 파일명을 담는 리스트 선언
        List<String> uploadedImageNames = new ArrayList<>();

        /*
        변수 images는 MultipartFile 타입인 객체들이 모여있는 List
        for문을 통해 업로드할 이미지 파일의 수(images.size())만큼 이미지 파일명을 생성하고 서버에 저장
        */
        for(int i = 0; i < images.size(); i++) {
            // images.get(i): 리스트 images의 i번째 파일을 MultipartFile 타입인 변수 image에 할당
            MultipartFile image = images.get(i);

            // 이미지가 비어있는지 확인
            if (image.isEmpty()) {
                log.warn("빈 이미지 파일이 전달되었습니다."); // 로깅
                continue; // 빈 이미지 파일은 건너뜀
            }

            // 상품번호, UUID, 원본 이미지 파일명(multipartFile.getOriginalFilename())을 결합해 업로드할 이미지 파일명 생성
            String imageName = pno + "_" + UUID.randomUUID() + "_" + image.getOriginalFilename();

            /*
            Path 객체 타입인 addUploadPath를 String 타입으로 변환한 것과 이미지 파일명을 합쳐
            업로드할 이미지의 경로 생성. 이것을 Path 객체 타입인 imageUploadedPath에 대입
            */
            Path imageUploadedPath = Paths.get(addUploadPath.toString(), imageName);

            /*
            try-with-resource: try 안에 매개변수를 넣으면 불러온 파일을 자동으로 닫을 수 있음
            -> finally 블록에서 close()를 호출하지 않아도 됨
            */
            try (InputStream inputStream = image.getInputStream()) {
                /*
                업로드한 이미지 파일을 서버에 저장
                Files.copy(): 입력 스트림을 받아서 지정한 경로에 파일을 복사(=저장)
                (1) 파일을 저장하는 이유: 상품 조회 시 업로드한 이미지 파일을 불러와야하기 때문
                (2) 입력 스트림: image.getInputStream()
                    -> 업로드한 이미지 파일의 입력 스트림(데이터를 읽어오기 위한 객체)을 가져옴
                       = 업로드한 이미지 파일을 읽어온다
                (3) 지정한 경로: imageUploadedPath -> 이미지 파일을 저장할 서버의 경로

                getInputStream()은 IOException 처리를 꼭 해야함
                -> 파일을 읽어오지 못하는 경우가 생길 수 있기 때문
                   예외 처리를 하지 않으면 컴파일 에러 발생
                IOException: 입출력 작업 중에 발생할 수 있는 예외를 처리하기 위한 클래스
                */
                Files.copy(image.getInputStream(), imageUploadedPath);

                // # product 경로에 업로드할 경우 섬네일 생성
                if (path.equals("product")) {
                    // 첫 번째로 업로드한 이미지 파일(i=0)인 경우에만 섬네일 생성
                    if (i == 0) {
                        /*
                        getContentType()을 이용해
                        List<MultipartFile> images 안의 변수들 image의 타입이
                        'image/jpeg', 'image/png' … 인지 확인
                        */
                        String contentType = image.getContentType();

                        // 만약 contentType이 null값이 아니고 'image'로 시작한다면
                        if (contentType != null && contentType.startsWith("image")) {
                            /*
                            addUploadPath(uploadPath에 추가한 경로)와
                            문자열 s_ + 이미지 파일명(UUID를 이용해 생성한 것)을 합쳐 섬네일의 경로 생성
                            */
                            Path thumbnailPath = Paths.get(addUploadPath.toString(), "s_" + imageName);

                            /*
                            Thumbnails: 이미지 리사이징(=섬네일 생성)을 쉽게 해주는 라이브러리
                            of(): 리사이징을 할 이미지 지정 -> imageUploadedPath에 저장된 이미지 파일
                            imageUploadedPath.toFile(): Path 객체 타입인 imageUploadedPath 변수를 File 타입으로 변환
                            -> imageUploadedPath에 저장된 이미지 파일(업로드한 이미지 파일)을 읽어옴
                            */
                            Thumbnails.of(imageUploadedPath.toFile())
                                    /*
                                    size(): 생성할 섬네일의 너비와 높이 지정
                                    (1) 단위: px      (2) width 기준으로 비율 유지
                                    */
                                    .size(300,300)
                                    /*
                                    toFile(): 리사이즈된 이미지를 파일로 저장
                                    thumbnailPath.toFile(): thumbnailPath(Path 타입)를 File 타입으로 변환
                                    -> toFile() 메서드는 File 객체 타입을 매개변수로 받기 때문에
                                    thumbnailPath를 .toFile()을 이용해 File 객체 타입으로 변환
                                    */
                                    .toFile(thumbnailPath.toFile());

                                    /*
                                    ★ .size() 뒤의 .toFile()과 thumbnailPath 뒤에 오는 .toFile()은 다른 역할을 함
                                    각각 다른 객체에 속하기 때문에 글자만 똑같을 뿐 아예 다른 메서드임
                                    (전자: Thumbnails 클래스의 메서드, 후자: Path 객체의 메서드)
                                    */
                        }
                    }
                }

                // 업로드된 이미지 파일명을 리스트에 담음
                uploadedImageNames.add(imageName);

            } catch (IOException e) {
                // 람다식 안에서 예외를 직접 처리할 수 없기 때문에 catch문을 써야함
                // 예외가 발생했을 때 발생 원인을 파악할 수 있도록 발생한 예외의 스택 트레이스를 출력
                e.printStackTrace();
                // 나중에 사용자 정의 Exception을 발생시켜야함
            }
        }

        return uploadedImageNames;
    }

    /*
    # 업로드한 이미지 파일을 브라우저에서 볼 수 있게 하는 메서드
    ResponseEntity 클래스: 내장 클래스. HTTP 응답을 생성할 때 사용
    ResponseEntity<Resource>: 본문이 파일 리소스(Resource)인 응답
    응답 본문: 클라이언트가 서버에 요청을 보냈을 때, 서버가 클라이언트에게 보내는 실제 데이터
    */
    public ResponseEntity<Resource> getImages(String path, String imageName) {

        // 경로가 추가된 addUploadPath 변수 선언
        String addUploadPath = uploadPath + File.separator + path;

        /*
        Resource: 파일이나 리소스에 접근하고 파일 읽기 및 쓰기 작업을 수행할 수 있는 인터페이스
        FileSystemResource: Resource 타입의 클래스. 매개변수는 path
        파일이 로컬에 저장되어 있을 때 그 파일을 읽거나 쓸 수 있도록 함
        여기선 FileSystemResource 객체=변수 resource: 업로드된 이미지 파일의 경로에 대한 '정보'를 가지고 있음
        path: 파일 경로를 '나타내는' 문자열. 텍스트일 뿐임
        */
        Resource resource = new FileSystemResource(Paths.get(addUploadPath, imageName));

        // 파일이 로컬에 저장되어있지 않다면 resource가 default.png 파일을 가리키게 함
        if(!resource.exists()) {
            resource = new FileSystemResource(Paths.get(uploadPath, "default.png"));
        }

        /*
        HttpHeaders: HTTP 요청이나 응답의 헤더 정보를 담는 클래스
        새로운 HttpHeaders 객체를 HttpHeaders 타입인 변수 headers에 대입
        -> (1) HttpHeaders headers: HttpHeaders 타입인 변수 headers를 선언
           (2) new HttpHeaders(): HttpHeaders 타입인 객체 생성
           (3) 변수 초기화: 생성한 HttpHeaders 타입인 객체를 변수 headers에 대입
        */
        HttpHeaders headers = new HttpHeaders();

        try {
            /*
            headers.add(): 변수 headers에 헤더 정보 중 하나인 Content-Type을 추가
            (1) Content-Type: 클라이언트나 서버가 주고받는 데이터가 어떤 형식인지 알려줌 ex)image/jpeg, image/png…
            (2) Files.probeContentType(): Content-Type의 값인 MIME 타입

            probeContentType(): Files 클래스 안에 있음
            (1) 파일의 MIME 타입(콘텐츠 유형)을 판별하는 메서드
            (2) 매개변수는 Path 타입의 객체 -> MIME 타입을 알아내려는 파일의 경로
            (3) 반환 값: 문자열. MIME 타입을 알 수 없는 경우 null 반환

            resource.getFile(): 변수 resource(Resource 타입)가 가리키는 경로에 있는 실제 파일 객체(File) 반환
            resource.getFile().toPath(): 그 파일 객체를 Path 객체로 변환
            -> Resource 타입은 직접 Path 타입으로 변환할 수 없으므로 실제 파일을 가져온 다음 변환해야함
            */
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            /*
            이미지 파일이 존재하지 않거나 파일 형식을 결정할 수 없을 때 발생하는 예외 처리
            ResponseEntity.internalServerError(): HTTP 500 상태 코드(500 Internal Server Error)를 가진 ResponseEntity 객체 생성
            -> 서버에서 발생한 일반적인 오류를 나타냄
            build(): 응답 본문에 해당 오류 내용 포함 없이 상태 코드만 반환
            -> 에러 상황이기 때문. 본문은 비어있는 상태
            */
            log.error("에러:{}",e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        /*
        ResponseEntity.ok(): HTTP 200 상태 코드(200 OK)를 가진 ResposeEntity 객체 생성
        -> 요청이 성공적으로 처리되었음을 나타내는 응답
        headers(headers): 위 응답에 추가적인 헤더 정보(여기선 MIME 타입)을 포함
        body(resource): 위 응답 본문에 resource를 포함
        ∴ HTTP 200 상태 코드, 헤더 정보(MIME 타입), 본문 내용(업로드된 이미지 파일의 정보)을
          가지고 있는 ResponseEntity 객체 반환
        */
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /*
    # 서버에 업로드한 이미지 파일들을 삭제하는 메서드
    첨부파일은 수정이라는 개념이 존재하지 않으므로 이미지 변경을 원할 경우 삭제 후 새로운 파일 업로드
    업로드한 이미지의 경로(addUploadedPath)를 알고 있기 때문에
    이미지 파일명(imageName)만 매개변수로 받아도 Path 객체를 구해
    서버에 업로드한 이미지 파일을 찾아서 삭제할 수 있음
    */
    public void deleteImages(String path, List<String> uploadedImageNames) {

        // 경로가 추가된 addUploadPath 변수 선언
        String addUploadPath = uploadPath + File.separator + path;

        //업로드한 이미지 파일이 없다면 삭제할 이미지 파일도 없으므로 메서드 종료
        if (uploadedImageNames == null || uploadedImageNames.isEmpty()) {
            return;
        }

        // imageName: 업로드한 이미지 파일명들이 담긴 리스트 안에 있는 하나의 이미지 파일명
        uploadedImageNames.forEach(imageName -> {
            // 이미지 파일명을 이용해 섬네일 파일명 변수 생성
            String thumbnailName = "s_" + imageName;
            // 섬네일이 업로드된 Path 객체 구하기
            Path thumbnailUploadedPath = Paths.get(addUploadPath, thumbnailName);
            // 이미지 파일이 업로드된 Path 객체 구하기
            Path imageUploadedPath = Paths.get(addUploadPath, imageName);

            try {
                /*
                deleteIfExists(): Files 클래스에 포함된 메서드
                (1) 매개변수로 Path 타입 객체를 받음
                (2) 업로드한 이미지 파일(섬네일 파일)의 Path 객체가 존재하면
                    그 Path 객체가 가리키는 실제 파일 삭제
                */
                Files.deleteIfExists(thumbnailUploadedPath);
                Files.deleteIfExists(imageUploadedPath);
            } catch (IOException e) {
                /*
                예외가 발생했을 때 현재 메서드를 종료하고 발생한 예외를 상위 호출자에게 알려줌
                마지막에 return할 값이 없으므로(=프로그램의 흐름을 계속 유지할 필요가 없으므로)
                e.printStackTrace()를 쓰지 않음
                */
                throw new RuntimeException(e.getMessage());
            }
        });
    }

}