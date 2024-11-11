package com.lab.darackbang.dto.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

// 공지사항 검색 조건 DTO
@Schema
@Getter
@Setter
@ToString
public class NoticeSearchDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String contents;
}
