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
@Table(name = "tbl_product_image")
public class ProductImage implements Serializable {

    private static final long serialVersionUID = 1L;
    //상품이미지아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //이미지파일명
    @Column(name = "product_file_name", nullable = false)
    private String productFileName;

    //상품아이디(상품정보 FK)
    //@ManyToOne(fetch = FetchType.LAZY): Product에서 ProductImage를 접근할 때만 지연 로딩
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product product;
}
