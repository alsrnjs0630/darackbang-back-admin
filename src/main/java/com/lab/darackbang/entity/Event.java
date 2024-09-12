package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "tbl_event")
public class Event {
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
    @Builder.Default
    private String eventState = "02";

    //이벤트시작일
    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    //이벤트종료일
    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    //등록일
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    //수정일
    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    private LocalDate updatedDate;
}
