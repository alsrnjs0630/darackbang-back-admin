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
@Table(name = "tbl_qanda_comment")
public class QandaComment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    // QandA 답글 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // QandA 아이디
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qanda_id", nullable = false)
    private Qanda qanda;

    // 내용
    @Column(name = "contents", nullable = false, length = 1000)
    private String contents;

}
