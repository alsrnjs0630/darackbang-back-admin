package com.lab.darackbang.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.lab.darackbang.entity.CommonGroupCode} entity.
 */
@Schema(description = "공통그룹코드")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CommonGroupCodeDTO implements Serializable {
    /**
     * 공통그룹코드
     */
    @NotNull
    @Size(max = 30)
    @Schema(description = "공통그룹코드", required = true)
    private String commonGroupCode;

    /**
     * 공통그룹코드명
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "공통그룹코드명", required = true)
    private String commonGroupCodeName;


    @Schema(description = "공통코드리스트", required = false)
    private List<CommonCodeDTO> commonCodes;

}
