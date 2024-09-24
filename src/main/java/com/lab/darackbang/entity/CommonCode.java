package com.lab.darackbang.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_common_code")
@IdClass(CommonCodeKey.class)
public class CommonCode implements Serializable {

    private static final long serialVersionUID = 1L;
    // 코드 -> 공통코드명세서 참고
    @Id
    @Column(name = "common_code", nullable = false, length = 20)
    private String commonCode;

    // 코드명
    @Column(name = "common_code_name", nullable = false, length = 50)
    private String commonCodeName;

    // 사용여부 (default 1 : 사용, 0 : 미사용)
    @Column(name = "is_used", nullable = false, length = 1)
    @ColumnDefault("1")
    private Boolean isUsed;

    // 그룹코드
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "common_group_code", nullable = false)
    @JsonIgnoreProperties(value = {"commonCodes"}, allowSetters = true)
    private CommonGroupCode commonGroupCode;

}
