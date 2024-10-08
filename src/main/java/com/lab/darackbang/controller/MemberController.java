package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.dto.member.MemberReqDTO;
import com.lab.darackbang.dto.member.MemberSearchDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductReqDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("admin/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public PageDTO<MemberDTO, MemberSearchDTO> list(@ModelAttribute MemberSearchDTO memberSearchDTO,
                                                    @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("사용자 리스트");
        
        return memberService.findAll(memberSearchDTO, pageable);
    }

    @GetMapping("/{id}")
    public MemberReqDTO get(@PathVariable Long id) {
        return memberService.findOne(id);
    }

    @PutMapping("")
    public Map<String, String> update(MemberDTO memberDTO){
        return memberService.update(memberDTO);
    }


    /**
     * 사용자 제활성화
     * @param id
     * @return
     */
    @PutMapping("/active/{id}")
    public Map<String, String> active(@PathVariable Long id){
        return memberService.active(id);
    }

    /**
     * 사용자 블랙컨슈머 해제
     * @param id
     * @return
     */
    @PutMapping("/unblacklist/{id}")
    public Map<String, String> unblacklist(@PathVariable Long id){
        return memberService.unblacklist(id);
    }

    /**
     * 사용자 블랙컨슈머 지정
     * @param id
     * @return
     */
    @PutMapping("/blacklist/{id}")
    public Map<String, String> blacklist(@PathVariable Long id){
        return memberService.blacklist(id);
    }
    /**
     * 사용자 탈퇴
     * @param id
     * @return
     */
    @PutMapping("/withdraw/{id}")
    public Map<String, String> withdraw(@PathVariable Long id){
        return memberService.withdraw(id);
    }
}
