package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_product_review")
public class ProductReview   extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @ColumnDefault("0")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    // 구매후기 이미지 매핑 설정
    @OneToMany(mappedBy = "productReview")
    @ToString.Exclude
    private List<ProductReviewImage> productReviewImages;
}
