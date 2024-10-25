package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.event.EventSearchDTO;
import com.lab.darackbang.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

/**
 * EventCriteria 클래스는 EventSearchDTO 엔티티에 대한 검색 조건(Specification)을 정의하는 클래스입니다.
 * 이 클래스는 동적 쿼리를 생성하는 데 사용되며, 다양한 검색 필터를 제공하여
 * 데이터베이스에서 원하는 조건에 맞는 데이터를 조회할 수 있도록 합니다.
 */
@Slf4j
public class EventCriteria {

    public static Specification<Event> byCriteria(EventSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {
            Specification<Event> spec = Specification.where(null);

            // title 필터 추가
            if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
                log.info("이벤트 제목: {}", dto.getTitle());
                spec = spec.and((root1, query1, cb) -> cb.like(root.get("title"), "%" + dto.getTitle() + "%"));
            }

            // contents 필터 추가
            if (dto.getContents() != null && !dto.getContents().isEmpty()) {
                log.info("이벤트 내용: {}", dto.getContents());
                spec = spec.and((root1, query1, cb) -> cb.like(root.get("contents"), "%" + dto.getContents() + "%"));
            }

            // eventState 필터 추가
            if (dto.getEventState() != null && !dto.getEventState().isEmpty()) {
                log.info("이벤트 상태: {}", dto.getEventState());
                spec = spec.and((root1, query1, cb) -> cb.like(root.get("eventState"), "%" + dto.getEventState() + "%"));
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
