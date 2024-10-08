package com.lab.darackbang.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class OrderSearchDTO {


    //총구매액
    @Schema(description = "총구매액", required = false)
    private Integer totalOrderPrice;

    @Schema(description = "구매자", required = false)
    private String userEmail;

}
