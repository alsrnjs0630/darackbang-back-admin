package com.lab.darackbang.dto.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Schema(description = "공지사항DTO")
@Getter
@Setter
@ToString
public class NoticeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "공지사항 인덱스 번호", required = true)
    private Long id;

    @NotNull
    @Size(max = 50)
    public String title;

    @NotNull
    @Size(max = 1000)
    public String contents;

    @NotNull
    @Size(max = 1)
    @Schema(description = "삭제 유무", defaultValue = "0")
    public Boolean isDelete;

    @NotNull
    @Schema(description = "등록일")
    public LocalDate createdDate;

    @NotNull
    @Schema(description = "수정일")
    public LocalDate updatedDate;
}
