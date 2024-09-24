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
@Table(name = "tbl_wish_list")
public class WishList extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 관심상품아이디
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

//    // 삭제유무 ( default 0 : 등록, 1 : 삭제 )
//    @Column(name = "is_deleted", nullable = false, length = 1)
//    @Builder.Default
//    private Boolean isDeleted = false;

}
