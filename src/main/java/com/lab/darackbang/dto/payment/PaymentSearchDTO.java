package com.lab.darackbang.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PaymentSearchDTO {

    //총구매액
    @Schema(description = "결제수단", required = false)
    private String pgProvider;

    @Schema(description = "구매액", required = false)
    private Integer paidAmount;

    @Schema(description = "결제성공여부", required = false)
    private Boolean success;

    @Schema(description = "주문자명", required = false)
    private String buyerName;

}
