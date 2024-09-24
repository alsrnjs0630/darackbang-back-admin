package com.lab.darackbang.dto.subscribe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Schema(description = "구독정보를 검색하기 위한 DTO")  // DTO 클래스에 대한 설명
public class SubscribeSearchDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "구독아이디", example = "001")  // 구독아이디에 대한 설명
    private Long id;

    @Schema(description = "상품명", example = "녹차")  // productName 필드에 대한 설명
    private String productName;

    @Schema(description = "구매수량", example = "3") // subQuantity 필드에 대한 설명
    private Integer subQuantity;

    @Schema(description = "구매가격", example = "30000") // subPrice 필드에 대한 설명
    private Integer subPrice;

    @Schema(description = "구독상태", example = "01") // subState 필드에 대한 설명
    private String subState;

}
