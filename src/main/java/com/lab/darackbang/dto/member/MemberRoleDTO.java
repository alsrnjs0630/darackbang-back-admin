package com.lab.darackbang.dto.member;

import com.lab.darackbang.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link com.lab.darackbang.entity.MemberRole} entity.
 */
@Schema(description = "회원롤정보")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class MemberRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 인덱스번호
     */
    @NotNull
    @Schema(description = "인덱스번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 사용자 롤
     */
    @NotNull
    @Schema(description = "회원롤", requiredMode = Schema.RequiredMode.REQUIRED)
    private Role role;

}
