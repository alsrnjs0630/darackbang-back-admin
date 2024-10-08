package com.lab.darackbang.service.member;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.dto.member.MemberReqDTO;
import com.lab.darackbang.dto.member.MemberSearchDTO;
import org.springframework.data.domain.Pageable;
import java.util.Map;


public interface MemberService {


    PageDTO<MemberDTO, MemberSearchDTO> findAll(MemberSearchDTO searchDTO, Pageable pageable);

    MemberReqDTO findOne(Long id);

    Map<String,String> update(MemberDTO memberDTO);

    Map<String,String> withdraw(Long id);

    Map<String,String> blacklist(Long id);

    Map<String,String> unblacklist(Long id);

    Map<String,String> active(Long id);

}
