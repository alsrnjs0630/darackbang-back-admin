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
@Table(name = "tbl_qanda")
public class Qanda extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @ColumnDefault("0")
    @Column(name = "is_deleted", nullable = false, length = 1)
    private Boolean isDeleted;

    // QandA 이미지 (qanda_image)테이블 매핑 설정
    @OneToMany(mappedBy = "qanda")
    @ToString.Exclude
    private List<QandaImage> qandaImages;

    // QandA 답글 테이블 매핑 설정
    @OneToOne(mappedBy = "qanda")
    private QandaComment qandaComment;

}
