package com.lab.darackbang.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_common_code")
@IdClass(CommonCodeKey.class)
public class CommonCode {
    // 코드 -> 공통코드명세서 참고
    @Id
    @Column(name = "common_code", nullable = false, length = 20)
    private String commonCode;

    // 코드명
    @Column(name = "common_code_name", nullable = false, length = 50)
    private String commonCodeName;

    // 사용여부 (default 1 : 사용, 0 : 미사용)
    @Column(name = "is_used", nullable = false, length = 1)
    @Builder.Default
    private Boolean isUsed = true;

    // 그룹코드
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "common_group_code", nullable = false)
    @JsonIgnoreProperties(value = {"commonCodes"}, allowSetters = true)
    private CommonGroupCode commonGroupCode;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CommonCode that = (CommonCode) o;
        return getCommonCode() != null && Objects.equals(getCommonCode(), that.getCommonCode());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
