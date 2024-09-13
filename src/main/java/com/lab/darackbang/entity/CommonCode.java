package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "tbl_common_code")
public class CommonCode {
    // 코드 -> 공통코드명세서 참고
    @Id
    @Column(name = "common_code", nullable = false, length = 20)
    private String commonCode;

    // 그룹코드
    @ManyToOne
    @JoinColumn(name = "group_code", nullable = false)
    private GroupCode groupCode;

    // 코드명
    @Column(name = "common_code_name", nullable = false, length = 50)
    private String commonCodeName;

    // 사용여부 (default 1 : 사용, 0 : 미사용)
    @Column(name = "is_used", nullable = false, length = 1)
    private Boolean isUsed = true;
}
