package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_main_category")
public class MainCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 대분류카테고리코드 code T01(잎차), T02(티백), T03(열매)
    @Id
    @Column(name = "main_cate_code", nullable = false, length = 20)
    private String mainCategoryCode;

    // 대분류카테고리명
    @Column(name = "main_cate_name", nullable = false, length = 50)
    private String mainCategoryName;

    // subCategory 중분류카테고리 테이블 매핑 설정
    @OneToMany(mappedBy = "mainCategory")
    @ToString.Exclude
    private List<SubCategory> subCategories;
}
