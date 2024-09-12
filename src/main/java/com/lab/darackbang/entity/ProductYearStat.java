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
@Table(name = "tbl_prd_year_stat")
public class ProductYearStat {

    // 연별아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 상품명
    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    // 년
    @Column(name = "year", nullable = false, length = 4)
    private String year;

    // 판매액
    @Column(name = "sale_total_price", nullable = false)
    private Integer saleTotalPrice;

    //등록일
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    //수정일
    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    private LocalDate updatedDate;
}
