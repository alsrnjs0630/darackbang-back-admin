package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_subscribe")
public class Subscribe extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    // 구독 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 회원아이디
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 상품아이디
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 구매수량
    @Column(name = "sub_quantity", nullable = false, length = 7)
    private Integer subQuantity;

    // 구독 금액
    @Column(name = "sub_price", nullable = false, length = 7)
    private Integer subPrice;

    // 구독일
    @Column(name = "subscribe_date", nullable = false)
    private LocalDate subscribeDate;

    // 결제일
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    // 배송일
    @Column(name = "shipping_date", nullable = false)
    private LocalDate shippingDate;//2024.10.29/

    // 구독 상태 ( default 01 : 구독, 02 : 해지신청, 03: 구독해지, 04: 구독중지)
    @ColumnDefault("'01'")
    @Column(name = "sub_state", nullable = false, length = 2)
    private String subState;

    // 구독중지일
    @Column(name = "suspend_date")
    private LocalDate suspendDate;

}
