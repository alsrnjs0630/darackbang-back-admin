package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "tbl_group_code")
public class GroupCode {

    // 그룹코드 -> 공통코드명세서 참고
    @Id
    @Column(name = "group_code", nullable = false, length = 20)
    private String groupCode;

    // 그룹코드명
    @Column(name = "group_code_name", nullable = false, length = 50)
    private String groupCodeName;

    // CommonCode 공통코드 테이블 매핑 설정
    @OneToMany(mappedBy = "groupCode")
    private List<CommonCode> commonCodes;
}
