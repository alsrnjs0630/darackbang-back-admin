package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_order_item")
public class OrderItem extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //구매아이템아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //상품 아이디
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    //구매내역아이디
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    //상품금액
    @Column(name = "product_price", nullable = false, length = 7)
    private Integer productPrice;

    //구매수량
    @Column(name = "order_quantity", nullable = false, length = 7)
    private Integer orderQuantity;

}
