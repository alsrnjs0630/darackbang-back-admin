package com.lab.darackbang.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tbl_order")
public class Order extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //구매내역아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //총구매액
    @Column(name = "total_order_price", nullable = false)
    private Integer totalOrderPrice;


    //구매일
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    //orderItems 매핑 설정
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnoreProperties("order")  // 순환 참조 방지
    private List<OrderItem> orderItems;

    // 회원아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
