package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_order_history")
public class OrderHistory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //구매내역아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 회원아이디
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //구매일
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    //orderItems 매핑 설정
    @OneToMany(mappedBy = "orderHistory")
    @ToString.Exclude
    private List<OrderItem> orderItems;
}
