package com.lab.darackbang.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.lab.darackbang.entity.Product}
 */
@Schema(description = "상품정보")
@Setter
@Getter
@ToString
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 인덱스번호
     */
    @NotNull
    @Schema(description = "인덱스 번호", required = true, example = "1")
    private Long id;

    /**
     * 상품 고유번호
     */
    @NotNull
    @Size(max = 12)
    @Schema(description = "상품 고유번호", example = "P001")
    private String pno;

    /**
     * 상품명
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "상품명", example = "홍차")
    private String productName;

    /**
     * 상품 상세 정보
     */
    @Size(max = 1000)
    @Schema(description = "상품 상세 정보", example = "고급 홍차 100g")
    private String productDetail;

    /**
     * 소비자가
     */
    @NotNull
    @Schema(description = "정가", example = "15000")
    private Integer retailPrice;

    /**
     * 판매 가격
     */
    @NotNull
    @Schema(description = "판매 가격", example = "10000")
    private Integer salePrice;

    /**
     * 유형
     */
    @Size(max = 10)
    @Schema(description = "상품 종류", example = "음료")
    private String type;

    /**
     * 주의 사항
     */
    @Size(max = 1000)
    @Schema(description = "주의 사항", example = "직사광선을 피해 보관")
    private String caution;

    /**
     * 상품 노출 여부
     */
    @NotNull
    @Schema(description = "상품 노출 여부", example = "true")
    private Boolean isVisible;

    /**
     * 제조사
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "제조사", example = "ABC 제조사")
    private String manufacture;

    /**
     * 브랜드
     */
    @Size(max = 50)
    @Schema(description = "브랜드", example = "ABC 브랜드")
    private String brand;

    /**
     * 원산지
     */
    @NotNull
    @Size(max = 20)
    @Schema(description = "원산지", example = "대한민국")
    private String origin;

    /**
     * 재료
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "재료", example = "홍차잎")
    private String material;

    /**
     * 카테고리
     */
    @NotNull
    @Size(max = 4)
    @Schema(description = "카테고리", example = "식음료")
    private String category;

    /**
     * 판매 회사
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "판매 회사", example = "판매회사명")
    private String saleCompany;

    /**
     * 판매 회사 정보
     */
    @Size(max = 1000)
    @Schema(description = "판매 회사 정보", example = "회사 정보")
    private String saleCompanyInfo;

    /**
     * 삭제 여부
     */
    @NotNull
    @Schema(description = "삭제 여부", example = "false")
    private Boolean isDeleted;

    /**
     * 품절 여부
     */
    @NotNull
    @Schema(description = "품절 여부", example = "false")
    private Boolean isSoldout;

    /**
     * 영양 정보
     */
    @Size(max = 1000)
    @Schema(description = "영양 정보", example = "칼로리: 0kcal")
    private String nutrition;

    /**
     * 수량
     */
    @NotNull
    @Schema(description = "수량", example = "100")
    private Integer quantity;

    /**
     * 패키지당 수량
     */
    @Schema(description = "패키지당 수량", example = "1")
    private Integer packageQuantity;

    /**
     * 유통기한
     */
    @NotNull
    @Schema(description = "유통기한", example = "2024-12-31")
    private LocalDate expirationDate;

    /**
     * 제조일자
     */
    @NotNull
    @Schema(description = "제조일자", example = "2023-12-31")
    private LocalDate manufactureDate;

    /**
     * GMO 여부
     */
    @NotNull
    @Schema(description = "GMO 여부", example = "false")
    private Boolean isGmo;

    /**
     * 용량(ml)
     */
    @Schema(description = "용량(ml)", example = "500")
    private Integer volume;

    /**
     * 위시리스트 추가 횟수
     */
    @NotNull
    @Schema(description = "위시리스트 추가 횟수", example = "200")
    private Integer wishCount;

    /**
     * 상품 이미지 리스트
     */
    @Schema(description = "상품 이미지 리스트")
    private List<ProductImageDTO> productImages;

    /**
     * 생성일자
     */
    @Schema(description = "생성일자", example = "2023-01-01T10:00:00")
    private LocalDateTime createdDate;

    /**
     * 수정일자
     */
    @Schema(description = "수정일자", example = "2023-01-02T10:00:00")
    private LocalDateTime updatedDate;

    @Schema(description = "상품 이미지 파일")
    private List<MultipartFile> productImageFiles;

    @Schema(description = "상품 이미지 파일명")
    private List<String> uploadFileNames;
}
