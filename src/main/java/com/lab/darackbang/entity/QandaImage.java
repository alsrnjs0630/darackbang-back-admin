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
@Table(name = "tbl_qanda_image")
public class QandaImage implements Serializable {

    private static final long serialVersionUID = 1L;
    // 이미지아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // QandA 아이디
    @ManyToOne
    @JoinColumn(name = "qanda_id", nullable = false)
    private Qanda qanda;

    // 이미지파일명
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
}
