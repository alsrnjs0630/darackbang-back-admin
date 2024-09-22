package com.lab.darackbang.security.dto;

import com.lab.darackbang.entity.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * A DTO for the {@link org.springframework.security.core.userdetails.User} entity.
 */
@Schema(description = "로그인정보")
@Getter
@Setter
@ToString
public class LoginDTO extends User {

    private static final long serialVersionUID = 1L;

    /**
     * 회원로그인ID
     */
    @NotNull
    @Size(max = 120)
    @Schema(description = "회원로그인ID")
    private String userEmail;

    /**
     * 패스워드
     */
    @NotNull
    @Size(max = 255)
    @Schema(description = "패스워드", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    /**
     * 회원이름
     */
    @NotNull
    @Size(max = 15)
    @Schema(description = "회원이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * 회원롤정보
     */
    @NotNull
    @Schema(description = "회원롤정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> roleNames = new ArrayList<>();

    public LoginDTO(String userEmail, String password,String name,  List<String> roleNames) {
        super(userEmail, password, roleNames.stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));

        this.userEmail = userEmail;
        this.password = password;
        this.name = name;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email", userEmail);
        dataMap.put("password", password);
        dataMap.put("nickname", name);
        dataMap.put("roleNames", roleNames);
        return dataMap;
    }
}
