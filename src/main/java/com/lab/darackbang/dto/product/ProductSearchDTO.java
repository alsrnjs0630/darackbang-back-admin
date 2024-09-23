package com.lab.darackbang.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Schema(description = "상품 검색을 위한 DTO")  // DTO 클래스에 대한 설명
public class ProductSearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "상품 이름", example = "홍차")  // productName 필드에 대한 설명
    private String productName;

    @Schema(description = "판매 가격", example = "10000")  // salePrice 필드에 대한 설명
    private Integer salePrice;

}