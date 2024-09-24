package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_event")
public class Event   extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //이벤트아이디
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

    //이미지파일명
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    //이벤트상태
    @Column(name = "event_state", nullable = false, length = 2)
    @ColumnDefault("'02'")
    private String eventState;

    //이벤트시작일
    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    //이벤트종료일
    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

}
