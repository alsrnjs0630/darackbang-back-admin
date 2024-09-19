package com.lab.darackbang.repository;

import com.lab.darackbang.entity.CommonCode;
import com.lab.darackbang.entity.CommonCodeKey;
import com.lab.darackbang.entity.CommonGroupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonCodeRepository extends
        JpaRepository<CommonCode, CommonCodeKey>,
        JpaSpecificationExecutor<CommonCode> {


    List<CommonCode> findAllByCommonGroupCodeAndIsUsedTrueOrderByCommonCodeDesc(CommonGroupCode commonGroupCode);

}
