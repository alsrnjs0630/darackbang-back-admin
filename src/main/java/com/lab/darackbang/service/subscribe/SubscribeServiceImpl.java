package com.lab.darackbang.service.subscribe;

import com.lab.darackbang.criteria.SubscribeCriteria;
import com.lab.darackbang.dto.subscribe.SubscribeDTO;
import com.lab.darackbang.dto.subscribe.SubscribeSearchDTO;
import com.lab.darackbang.entity.Subscribe;
import com.lab.darackbang.mapper.SubscribeMapper;
import com.lab.darackbang.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscribeServiceImpl implements SubscribeService{

    private final SubscribeRepository subscribeRepository;

    private final SubscribeMapper subscribeMapper;

    /**
     * 주어진 검색 조건 및 페이징 정보를 기반으로 모든 제품 목록을 조회합니다.
     *
     * @param searchDTO 제품 검색 조건을 담고 있는 DTO
     * @param pageable 페이징 처리를 위한 Pageable 객체
     * @return 검색 조건에 맞는 제품 목록을 페이지 단위로 반환
     */

    @Override
    @Transactional(readOnly = true)
    public Page<SubscribeDTO> findAll(SubscribeSearchDTO searchDTO, Pageable pageable) {
        // SubscribeSearchDTO에 기반하여 Specification<Subscribe> 객체 생성
        Specification<Subscribe> spec = SubscribeCriteria.byCriteria(searchDTO);
        // JPA 리포지토리를 사용하여 페이징을 적용한 상품 목록 조회 후, SubscribeMapper를 통해 SubscribeDTO로 변환
        return subscribeRepository.findAll(spec, pageable).map(subscribeMapper::toDTO);
    }
}
