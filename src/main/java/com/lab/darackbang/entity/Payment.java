package com.lab.darackbang.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_payment")
public class Payment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 결제아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //신용카드 승인번호
    @Column(name = "apply_num", nullable = true, length = 1000)
    private String applyNum;

    //가상계좌 은행명
    @Column(name = "bank_name", nullable = true, length = 150)
    private String bankName;

    //주문자 주소
    @Column(name = "buyer_addr", nullable = true, length = 255)
    private String buyerAddr;

    //주문자 이메일
    @Column(name = "buyer_email", nullable = true, length = 255)
    private String buyerEmail;

    //주문자명
    @Column(name = "buyer_name", nullable = true, length = 50)
    private String buyerName;

    //주문자 우편번호
    @Column(name = "buyer_postcode", nullable = true, length = 7)
    private String buyerPostcode;

    //주문자 연락처
    @Column(name = "buyer_tel", nullable = true, length = 13)
    private String buyerTel;

    //카드명
    @Column(name = "card_name", length = 255)
    private String cardName;

    //카드 번호
    @Column(name = "card_number", nullable = true, length = 19)
    private String cardNumber;
    //할부 개월
    //기본값이 00
    @Column(name = "card_quota", nullable = true, length = 2)
    private String cardQuota;

    //기본값이 00
    @Column(name = "currency", nullable = true, length = 10)
    private String currency;

    //가맹점 임의 지정 데이터
    @Column(name = "custom_data", length = 1000)
    private String customData;

    //회원번호
    @Column(name = "customer_uid", nullable = true, length = 50)
    private String customerUid;

    // IamPort 고유 결제 번호
    //success가 false이고 사전 validation에 실패한 경우, imp_uid는 null일 수 있음
    @Column(name = "imp_uid", nullable = true, length = 20)
    private String impUid;

    //주문번호
    @Column(name = "merchant_uid", nullable = true, length = 50)
    private String merchantUid;

    //상품명
    @Column(name = "name", nullable = true, length = 60)
    private String name;

    //결제금액
    @Column(name = "paid_amount", nullable = true, length = 7)
    private Integer paidAmount;

    //결제승인시각, 결제일
    // Unix timestamp 사용시 string type, Mapper로 LocalDateTime으로 변환
    @Column(name = "paid_at", nullable = true, length = 10)
    private LocalDateTime paidAt;

    //결제수단 구분코드
    @Column(name = "paid_method", nullable = true, length = 15)
    private String payMethod;

    //PG사 구분코드
    @Column(name = "pg_provider", nullable = true, length = 15)
    private String pgProvider;

    //PG사 거래번호
    @Column(name = "pg_tid", nullable = true, length = 30)
    private String pgTid;

    // 결제 타입
    // 일반결제인 경우 무조건 payment로 전달
    @Column(name = "pg_type", nullable = true, length = 15)
    private String pgType;

    //거래 매출전표 URL
    @Column(name = "receipt_url", nullable = true, length = 255)
    private String receiptUrl;

    //카드 유효기간
    @Column(name = "expiration_date", nullable = true, length = 5)
    private LocalDate expirationDate;

    // 결제상태 (default 01 : 결제 대기, 02 : 결제 성공, 03 : 결제 실패)
    @ColumnDefault("'01'")
    @Column(name = "status", nullable = true, length = 12)
    private String status;

    //결제 성공여부
    //결제승인 혹은 가상계좌 발급이 성공한 경우 true
    @Column(name = "success", nullable = true)
    private Boolean success;

    // 결제 실패 원인
    @Column(name = "fail_reason", nullable = true, length = 1000)
    private String failReason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
