package com.lab.darackbang.dto;



import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the {@link com.lab.darackbang.entity.CommonCode} entity.
 */
@Schema(description = "공통코드")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CommonCodeDTO implements Serializable {

    /**
     * 공통코드
     */
    @NotNull
    @Size(max = 20)
    @Schema(description = "공통코드", required = true)
    private String commonCode;

    /**
     * 공통코드명
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "공통코드명", required = true)
    private String commonCodeName;

    /**
     * 활성화여부
     */
    @NotNull
    @Schema(description = "사용유무", required = true)
    private Boolean isUsed;

    private CommonGroupCodeDTO commonGroupCode;
}
