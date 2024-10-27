package com.lab.darackbang.service.event;

import com.lab.darackbang.common.utils.CustomFileUtil;
import com.lab.darackbang.criteria.EventCriteria;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import com.lab.darackbang.entity.Event;
import com.lab.darackbang.mapper.EventMapper;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CustomFileUtil customFileUtil;
    private final PageMapper pageMapper;

    @Override
    public Map<String, String> create(EventDTO eventDTO, MultipartFile file) throws IOException {

        // 매퍼로 DTO를 Entity로 변환
        Event event = eventMapper.toEntity(eventDTO);

        // customFileUtil 파일 저장 메소드 사용. 지정된 경로로 파일 copy하고 저장된 파일명 반환
        String fileName = customFileUtil.saveEventFile(file);

        event.setFileName(fileName);

        eventRepository.save(event);

        return Map.of("RESULT", "SUCCESS");
    }

    /**
     * 주어진 검색 조건 및 페이징 정보를 기반으로 모든 이벤트 목록을 조회합니다.
     *
     * @param searchDTO 제품 검색 조건을 담고 있는 DTO
     * @param pageable  페이징 처리를 위한 Pageable 객체
     * @return 검색 조건에 맞는 제품 목록을 페이지 단위로 반환
     */
    @Override
    @Transactional(readOnly = true)
    public PageDTO<EventDTO, EventSearchDTO> findAll(EventSearchDTO searchDTO, Pageable pageable) {
        // ProductSearchDTO에 기반하여 Specification<Product> 객체 생성

        log.info(searchDTO.toString());

        Specification<Event> spec = EventCriteria.byCriteria(searchDTO);

        //페이지 번호(프론트에서는 1부터 시작하지만 실제로는 현재 페이지번호 -1)
        int pageNumber = (pageable.getPageNumber() < 1) ? 0 : pageable.getPageNumber() - 1;
        Pageable correctedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());

        // JPA 리포지토리를 사용하여 페이징을 적용한 상품 목록 조회 후, ProductMapper를 통해 ProductDTO로 변환
        return pageMapper.toDTO(eventRepository.findAll(spec, correctedPageable).map(eventMapper::toDTO), searchDTO);
    }

    // 이벤트 상세정보
    @Override
    public EventDTO read(Long id) {
        return eventMapper.toDTO(eventRepository.findById(id).orElse(null));
    }

    // 이벤트 이미지 보기
    @Override
    public ResponseEntity<Resource> getFile(String fileName) {
        return customFileUtil.getEventFile(fileName);
    }

    @Override
    public Map<String, String> update(EventDTO eventDTO, MultipartFile file) throws IOException {
        return Map.of();
    }

}
