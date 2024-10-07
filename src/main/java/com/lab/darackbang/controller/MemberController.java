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

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        return memberService.delete(id);
    }

    @PutMapping("")
    public Map<String, String> update(MemberDTO memberDTO){
        return memberService.update(memberDTO);
    }

}
