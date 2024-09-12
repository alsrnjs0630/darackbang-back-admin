package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "tbl_subcribe")
public class Subscribe {
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

    // 배송시작일
    @Column(name = "shipping_date", nullable = false)
    private LocalDate shippingDate;

    // 구독 상태 ( default 01 : 구독, 02 : 해지신청, 03: 구독해지, 04: 구독중지)
    @Builder.Default
    @Column(name = "sub_state", nullable = false, length = 2)
    private String subState = "01";

    // 구독중지일
    @Column(name = "suspend_date")
    private LocalDate suspendDate;

    // 등록일
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    // 수정일
    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    private LocalDate updatedDate;

    // payment (결제) 테이블 매핑 설정
    @OneToMany(mappedBy = "subscribe")
    private List<Payment> payments;

    // delivery (배송) 테이블 매핑 설정
    @OneToMany(mappedBy = "subscribe")
    private List<Delivery> deliveries;

    // exchagne (교환) 테이블 매핑 설정
    @OneToMany(mappedBy = "subscribe")
    private List<Exchange> exchanges;
}
