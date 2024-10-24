package com.lab.darackbang.dto.analyze;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageAnalyzeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 인덱스번호
     */
    @NotNull
    @Schema(description = "인덱스번호", required = true)
    private Long id;

    /**
     * 파일명
     */
    @NotNull
    @Schema(description = "파일명")
    private String fileName;

    /**
     * 등록일시
     */
    @NotNull
    @Schema(description = "등록일시",requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createdDate;

    /**
     * 수정일시
     */
    @NotNull
    @Schema(description = "수정일시",requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updatedDate;
}
