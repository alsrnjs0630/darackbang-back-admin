package com.lab.darackbang.dto.subscribe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "구독정보")
public class SubscribeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 인덱스번호
    @NotNull
    @Schema(description = "인덱스번호", required = true)
    private Long id;

    // 구매수량
    @NotNull
    @Size(max = 7)
    @Schema(description = "구매수량", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer subQuantity;

    //구독금액
    @NotNull
    @Size(max = 7)
    @Schema(description = "구독금액", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer subPrice;

    //구독일
    @NotNull
    @Schema(description = "구독일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate subscribeDate;

    //결제일
    @NotNull
    @Schema(description = "결제일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate paymentDate;

    //배송시작일
    @NotNull
    @Schema(description = "배송시작일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate shippingDate;

    //구독상태
    @NotNull
    @Size(max = 2)
    @Schema(description = "구독상태", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subState;

    //구독중지일
    @Schema(description = "구독중지일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate suspendDate;

    //등록일
    @NotNull
    @Schema(description = "등록일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate createdDate;

    //수정일
    @NotNull
    @Schema(description = "수정일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate updatedDate;
}
