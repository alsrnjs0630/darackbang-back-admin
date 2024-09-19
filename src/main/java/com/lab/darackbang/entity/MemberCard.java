package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "tbl_member_card")
public class MemberCard {

    // 회원카드정보 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 회원 아이디
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 카드번호
    @Column(name = "card_no", nullable = false, length = 16)
    private String cardNo;

    // 카드명
    @Column(name = "card_name", nullable = false, length = 150)
    private String cardName;

    // 카드사용자명
    @Column(name = "card_user_name", nullable = false, length = 150)
    private String cardUserName;

    // 유효기간
    @Column(name = "valid_date", nullable = false, length = 4)
    private String validDate;

    // 카드 유효성상태 (default 0 : 등록, 1 : 삭제)
    @Builder.Default
    @Column(name = "valid_state", nullable = false)
    private Boolean validState = false;

    // 등록일
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    // 수정일
    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate;
}
