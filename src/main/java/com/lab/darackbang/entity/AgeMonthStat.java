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
@Table(name = "tbl_age_month_stat")
public class AgeMonthStat {

    // 월별아이디
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

    // 월
    @Column(name = "month", nullable = false, length = 2)
    private String month;

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
