package com.lab.darackbang.service.member;

import com.lab.darackbang.criteria.MemberCriteria;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.dto.member.MemberReqDTO;
import com.lab.darackbang.dto.member.MemberSearchDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Product;
import com.lab.darackbang.exception.MemberNotFoundException;
import com.lab.darackbang.mapper.MemberMapper;
import com.lab.darackbang.mapper.MemberReqMapper;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PageMapper pageMapper;

    private final MemberMapper memberMapper;

    private final MemberReqMapper memberReqMapper;

    @Override
    public PageDTO<MemberDTO, MemberSearchDTO> findAll(MemberSearchDTO searchDTO, Pageable pageable) {

        Specification<Member> spec = MemberCriteria.byCriteria(searchDTO);

        //페이지 번호(프론트에서는 1부터 시작하지만 실제로는 현재 페이지번호 -1)
        int pageNumber = (pageable.getPageNumber() < 1) ? 0 : pageable.getPageNumber() - 1;

        Pageable correctedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());

        // JPA 리포지토리를 사용하여 페이징을 적용한 상품 목록 조회 후, ProductMapper를 통해 ProductDTO로 변환
        return pageMapper.toDTO(memberRepository.findAll(spec, correctedPageable).map(memberMapper::toDTO), searchDTO);
    }

    @Override
    public MemberReqDTO findOne(Long id) {
        return memberRepository.findById(id).map(memberReqMapper::toDTO).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public Map<String, String> delete(Long id) {
        return memberRepository.findById(id).map(product -> {
            product.setIsDeleted(true);
            memberRepository.save(product);
            return Map.of("RESULT", "SUCCESS");
        }).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public Map<String, String> update(MemberDTO memberDTO) {

        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(MemberNotFoundException::new);

        BeanUtils.copyProperties(memberDTO, member, "id","memberRoles","isDeleted");

        memberRepository.save(member);

        return Map.of("RESULT", "SUCCESS");
    }
}
