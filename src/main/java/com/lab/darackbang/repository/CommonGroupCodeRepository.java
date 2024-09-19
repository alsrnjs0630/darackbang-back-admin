package com.lab.darackbang.repository;

import com.lab.darackbang.entity.CommonGroupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonGroupCodeRepository extends
        JpaRepository<CommonGroupCode, String>,
        JpaSpecificationExecutor<CommonGroupCode> {


    Optional<CommonGroupCode> findByCommonGroupCode(String code);


}
