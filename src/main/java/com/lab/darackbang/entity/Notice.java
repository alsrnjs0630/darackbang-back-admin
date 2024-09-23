package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_notice")
public class Notice extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //공지사항아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //제목
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    //내용
    @Column(name = "contents", nullable = false, length = 1000)
    private String contents;

    //삭제유무
    @Builder.Default
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

}
