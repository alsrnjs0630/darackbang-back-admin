package com.lab.darackbang.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Schema(description = "이벤트")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class EventDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    * 이벤트 아이디 (인덱스 번호)
    * */
    @NotNull
    @Schema(description = "인덱스번호", required = true)
    private Long id;

    /*
    * 제목
    * */
    @NotNull
    @Size(max = 50)
    @Schema(description = "제목", required = true)
    private String title;

    /*
    * 내용
    * */
    @NotNull
    @Size(max = 1000)
    @Schema(description = "내용", required = true)
    private String contents;

    /*
    * 이미지 파일명
    * */
    @NotNull
    @Size(max = 255)
    @Schema(description = "이미지파일명", required = true)
    private String fileName;

    /*
    * 이벤트 상태
    * */
    @NotNull
    @Size(max = 2)
    @Schema(description = "이벤트상태", required = true)
    private String eventState;

    /*
    * 이벤트 시작일
    * */
    @Schema(description = "이벤트시작일")
    private LocalDate startDate;

    /*
    * 이벤트 종료일
    * */
    @Schema(description = "이벤트종료일")
    private LocalDate endDate;

}
