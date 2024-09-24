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
@Table(name = "tbl_payment")
public class Payment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 결제아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 결제번호
    @Column(name = "payment_id", nullable = false, length = 50)
    private String paymentId;

    // 회원 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @ToString.Exclude
    private Member member;

    // 결제금액
    @Column(name = "payment_price", nullable = false, length = 7)
    private Integer paymentPrice;

    // 결제상태 (default 01 : 결제 대기, 02 : 결제 성공, 03 : 결제 실패)
    @ColumnDefault("'01'")
    @Column(name = "payment_state", nullable = false, length = 2)
    private String paymentState;

    // 결제일
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    // 결제 실패 원인
    @Column(name = "fail_reason", length = 1000)
    private String failReason;

}
