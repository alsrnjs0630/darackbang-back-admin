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
@Table(name = "tbl_age_year_stat")
public class AgeYearStat extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 연별아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 연령대
    @Column(name = "age_group", nullable = false, length = 2)
    private String ageGroup;

    // 년
    @Column(name = "year", nullable = false, length = 4)
    private String year;

    // 판매액
    @Column(name = "sale_total_price", nullable = false)
    private Integer saleTotalPrice;

}
