package com.lab.darackbang.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
@Schema(description = "상품 이미지 정보 DTO")  // 클래스에 대한 설명 추가
public class ProductImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 상품 이미지 아이디
     */
    @Schema(description = "상품 이미지 아이디", example = "1")
    private Long id;

    /**
     * 이미지 파일명
     */
    @Schema(description = "이미지 파일명", example = "product_image.jpg")
    private String productFileName;

}