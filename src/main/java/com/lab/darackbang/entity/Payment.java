package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tbl_payment")
public class Payment {

    // 결제아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "paymentId = " + paymentId + ", " +
                "paymentPrice = " + paymentPrice + ", " +
                "paymentState = " + paymentState + ", " +
                "paymentDate = " + paymentDate + ", " +
                "failReason = " + failReason + ", " +
                "createdDate = " + createdDate + ", " +
                "updatedDate = " + updatedDate + ")";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Payment payment = (Payment) o;
        return getId() != null && Objects.equals(getId(), payment.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
