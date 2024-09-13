package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
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
@Table(name = "tbl_qanda")
public class Qanda {
    // QandA 아이디
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

    // 삭제 유무 (default 0 : 등록, 1: 삭제)
    @Builder.Default
    @Column(name = "is_deleted", nullable = false, length = 1)
    private Boolean isDeleted = false;

    // 등록일
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    // 수정일
    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    private LocalDate updatedDate;

    // QandA 이미지 (qanda_image)테이블 매핑 설정
    @OneToMany(mappedBy = "qanda")
    private List<QandaImage> qandaImages;

    // QandA 답글 테이블 매핑 설정
    @OneToOne(mappedBy = "qanda")
    private QandaComment qandaComment;

}
