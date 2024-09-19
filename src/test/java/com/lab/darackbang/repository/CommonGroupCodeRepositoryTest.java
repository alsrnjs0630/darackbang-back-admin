package com.lab.darackbang.repository;

import com.lab.darackbang.entity.CommonCode;
import com.lab.darackbang.entity.CommonCodeKey;
import com.lab.darackbang.entity.CommonGroupCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class CommonGroupCodeRepositoryTest {


    @Autowired
    private CommonGroupCodeRepository commonGroupCodeRepository;

    @Autowired
    private CommonCodeRepository commonCodeRepository;


    @Test
    void insertGroupCode() {
        CommonGroupCode commonGroupCode = CommonGroupCode.builder().commonGroupCode("MEMBER_STATE_CODE").commonGroupCodeName("회원상태코드").build();
        commonGroupCodeRepository.save(commonGroupCode);
    }


    @Test
    void insertGroupCode2() {
        CommonGroupCode commonGroupCode = CommonGroupCode.builder().commonGroupCode("PRODUCT_STATE_CODE").commonGroupCodeName("상품상태코드").build();
        commonGroupCodeRepository.save(commonGroupCode);
    }

    @Test
    void findAllCommonGroupCode(){

        List<CommonGroupCode> commonGroupCodeList = commonGroupCodeRepository.findAll();

        commonGroupCodeList.forEach(commonGroupCode ->{
            log.info("공통그룹코드 리스트:{}",commonGroupCode.toString());
            log.info("공통코드 리스트{}", commonGroupCode.getCommonCodes().toString());
        });
    }

    @Test
    void findByCommonGroupCode(){

        CommonGroupCode commonGroupCode = commonGroupCodeRepository.findById("MEMBER_STATE_CODE").orElseThrow();

        log.info("공통그룹코드 리스트:{}",commonGroupCode.toString());
        log.info("공통코드 리스트:{}", commonGroupCode.getCommonCodes().toString());

    }


}
