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
public class CommonCodeRepositoryTest {


    @Autowired
    private CommonGroupCodeRepository commonGroupCodeRepository;

    @Autowired
    private CommonCodeRepository commonCodeRepository;

    @Test
    void insertCommonCode(){

        CommonGroupCode commonGroupCode = commonGroupCodeRepository.findByCommonGroupCode("MEMBER_STATE_CODE").orElseThrow();

        CommonCode commonCode = CommonCode.builder().commonGroupCode(commonGroupCode).commonCode("01").commonCodeName("정상").isUsed(true).build();

        commonCodeRepository.save(commonCode);

        CommonCode commonCode2 = CommonCode.builder().commonGroupCode(commonGroupCode).commonCode("02").commonCodeName("탈퇴").isUsed(true).build();

        commonCodeRepository.save(commonCode2);
    }

    @Test
    void findAllCommonCode(){

        CommonGroupCode commonGroupCode = commonGroupCodeRepository.findByCommonGroupCode("MEMBER_STATE_CODE").orElseThrow();
        List<CommonCode> commonCodeList = commonCodeRepository.findAllByCommonGroupCodeAndIsUsedTrueOrderByCommonCodeDesc(commonGroupCode);

        commonCodeList.forEach(commonCode -> {
           log.info("공통 코드 리스트{}",commonCode.toString());
        });
    }

    @Test
    void updateCommonCode(){

        CommonCodeKey key = new CommonCodeKey();
        key.setCommonCode("01");
        key.setCommonGroupCode("MEMBER_STATE_CODE");

        CommonCode commonCode = commonCodeRepository.findById(key).orElseThrow();

        commonCode.setCommonCodeName("활동");

        commonCodeRepository.save(commonCode);

    }
}
