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
@Table(name = "tbl_prd_month_stat")
public class ProductMonthStat extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 월별아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //상품번호
    @Column(name = "pno", nullable = false, length = 12)
    private String pno;

    // 상품명
    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    // 년
    @Column(name = "year", nullable = false, length = 4)
    private String year;

    // 월
    @Column(name = "month", nullable = false, length = 2)
    private String month;

    // 판매액
    @Column(name = "sale_total_price", nullable = false)
    private Integer saleTotalPrice;
}
