package com.lab.darackbang.dto.payment;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lab.darackbang.controller.advice.UnixTimestampDeserializer;
import com.lab.darackbang.dto.product.ProductCartItemDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PaymentResDTO {

    //신용카드 승인번호
    @Schema(description = "신용카드 승인번호", required = false)
    private String applyNum;

    //가상계좌 은행명
    @Schema(description = "가상계좌 은행명", required = false)
    private String bankName;

    //주문자 주소
    @Schema(description = "주문자 주소", required = false)
    private String buyerAddr;

    //주문자 이메일
    @Schema(description = "주문자 이메일", required = false)
    private String buyerEmail;

    //주문자명
    @Schema(description = "주문자명", required = false)
    private String buyerName;

    //주문자 우편번호
    @Schema(description = "주문자 우편번호", required = false)
    private String buyerPostcode;

    //주문자 연락처
    @Schema(description = "주문자 연락처", required = false)
    private String buyerTel;

    //카드명
    @Schema(description = "카드명", required = false)
    private String cardName;

    //카드 번호
    @Schema(description = "/카드 번호", required = false)
    private String cardNumber;
    //할부 개월
    //기본값이 00
    @Schema(description = "할부 개월", required = false)
    private String cardQuota;

    //기본값이 00
    @Schema(description = "결제통화종류", required = false)
    private String currency;

    //가맹점 임의 지정 데이터
    @Schema(description = "가맹점 임의 지정 데이터", required = false)
    private String customData;

    //회원번호
    @Schema(description = "회원번호", required = false)
    private String customerUid;

    // IamPort 고유 결제 번호
    //success가 false이고 사전 validation에 실패한 경우, imp_uid는 null일 수 있음
    @Schema(description = "IamPort 고유 결제 번호", required = false)
    private String impUid;

    //주문번호
    @Schema(description = "주문번호", required = false)
    private String merchantUid;

    //상품명
    @Schema(description = "상품명", required = false)
    private String name;

    //결제금액
    @Schema(description = "결제금액", required = false)
    private Integer paidAmount;

    //결제승인시각, 결제일
    @Schema(description = "결제승인시각", required = false)
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private LocalDateTime paidAt;

    //결제수단 구분코드
    @Schema(description = "결제수단", required = false)
    private String payMethod;

    //PG사 구분코드
    @Schema(description = "PG사", required = false)
    private String pgProvider;

    //PG사 거래번호
    @Schema(description = "PG사거래번호", required = false)
    private String pgTid;

    // 결제 타입
    // 일반결제인 경우 무조건 payment로 전달
    @Schema(description = "결제 타입", required = false)
    private String pgType;

    //거래 매출전표 URL
    @Schema(description = "거래 매출전표 URL", required = false)
    private String receiptUrl;

    //카드 유효기간
    @Schema(description = "카드 유효기간", required = false)
    private LocalDate expirationDate;

    // 결제상태 (default 01 : 결제 대기, 02 : 결제 성공, 03 : 결제 실패)
    @Schema(description = "결제상태", required = false)
    private String status;

    //결제 성공여부
    //결제승인 혹은 가상계좌 발급이 성공한 경우 true
    @Schema(description = "결제 성공여부", required = false)
    private Boolean success;

    // 결제 실패 원인
    @Schema(description = "결제 실패 원인", required = false)
    private String failReason;

    @Schema(description = "주문상품정보", required = true)
    private List<ProductCartItemDTO> productCartItemList;
}
