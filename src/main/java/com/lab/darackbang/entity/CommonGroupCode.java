package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tbl_common_group_code")
public class CommonGroupCode {

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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "commonGroupCode = " + commonGroupCode + ", " +
                "commonGroupCodeName = " + commonGroupCodeName + ")";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CommonGroupCode that = (CommonGroupCode) o;
        return getCommonGroupCode() != null && Objects.equals(getCommonGroupCode(), that.getCommonGroupCode());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
