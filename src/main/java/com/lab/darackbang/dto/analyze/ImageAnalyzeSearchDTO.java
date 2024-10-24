package com.lab.darackbang.dto.analyze;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageAnalyzeSearchDTO {
    @Schema(description = "파일명")
    private String fileName;
}
