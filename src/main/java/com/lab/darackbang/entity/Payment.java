package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "tbl_payment")
public class Payment {

    // 결제아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 구독아이디
    @ManyToOne
    @JoinColumn(name = "subscribe_id", nullable = false)
    private Subscribe subscribe;

    // 결제번호
    @Column(name = "payment_id", nullable = false, length = 50)
    private String paymentId;

    // 결제금액
    @Column(name = "payment_price", nullable = false, length = 7)
    private Integer paymentPrice;

    // 결제상태 (default 01 : 결제 대기, 02 : 결제 성공, 03 : 결제 실패)
    @Builder.Default
    @Column(name = "payment_state", nullable = false, length = 2)
    private String paymentState = "01";

    // 결제일
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    // 결제 실패 원인
    @Column(name = "fail_reason", length = 1000)
    private String failReason;

    // 등록일
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    // 수정일
    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    private LocalDate updatedDate;
}
