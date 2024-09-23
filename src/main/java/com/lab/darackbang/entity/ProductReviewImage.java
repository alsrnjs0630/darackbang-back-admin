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
@Table(name = "tbl_product_review_image")
public class ProductReviewImage implements Serializable {

    private static final long serialVersionUID = 1L;
    // 이미지 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 구매후기아이디
    @ManyToOne
    @JoinColumn(name = "product_review_id", nullable = false)
    private ProductReview productReview;

    // 이미지 파일명
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
}
