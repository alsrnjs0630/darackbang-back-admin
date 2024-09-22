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
@Table(name = "tbl_sub_category")
public class SubCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    // 중분류카테고리코드 code TL001(잎차), TB001(티백), TF001(열매)
    @Id
    @Column(name = "sub_cate_code", nullable = false, length = 20)
    private String subCategoryCode;

    // 대분류 카테고리코드 FK
    @ManyToOne
    @JoinColumn(name = "main_cate_code", nullable = false)
    private MainCategory mainCategory;

    // 중분류카테고리명
    @Column(name = "sub_cate_name", nullable = false, length = 50)
    private String subCategoryName;
}
