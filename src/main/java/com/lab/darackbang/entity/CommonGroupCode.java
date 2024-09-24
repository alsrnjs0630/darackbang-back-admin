package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_common_group_code")
public class CommonGroupCode implements Serializable {

    private static final long serialVersionUID = 1L;

    // 그룹코드 -> 공통코드명세서 참고
    @Id
    @Column(name = "common_group_code", nullable = false, length = 20)
    private String commonGroupCode;

    // 그룹코드명
    @Column(name = "common_group_code_name", nullable = false, length = 50)
    private String commonGroupCodeName;

    // CommonCode 공통코드 테이블 매핑 설정
    @OneToMany(mappedBy = "commonGroupCode", fetch = FetchType.EAGER)
    private List<CommonCode> commonCodes;

}
