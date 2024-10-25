package com.lab.darackbang.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Schema(description = "이벤트 검색 DTO")
@Setter
@Getter
@ToString
public class EventSearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 이벤트 제목으로 검색
    @Schema(description = "이벤트 제목", example = "오픈")
    private String title;

    // 이벤트 내용으로 검색
    @Schema(description = "이벤트 내용")
    private String contents;

    // 이벤트 상태로 필터링
    @Schema(description = "이벤트 상태")
    private String eventState;
}
