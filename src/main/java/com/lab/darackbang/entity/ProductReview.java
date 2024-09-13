package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "tbl_product_review")
public class ProductReview {
    // 구매후기아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 회원아이디
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 상품아이디
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 제목
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    // 내용
    @Column(name = "contents", nullable = false, length = 1000)
    private String contents;

    // 삭제유무
    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    // 등록일
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    // 수정일
    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    private LocalDate updatedDate;

    // 구매후기 이미지 매핑 설정
    @OneToMany(mappedBy = "productReview")
    private List<ProductReviewImage> productReviewImages;
}
