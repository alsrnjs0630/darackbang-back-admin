package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.notice.NoticeSearchDTO;
import com.lab.darackbang.entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@Slf4j
public class NoticeCriteria {

    public static Specification<Notice> byCriteria(NoticeSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {
            Specification<Notice> spec = Specification.where(null);

            // title 필터 추가
            if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
                log.info("공지사항 제목: {}", dto.getTitle());
                spec = spec.and((root1, query1, cb) -> cb.like(root.get("title"), "%" + dto.getTitle() + "%"));
            }

            // contents 필터 추가
            if (dto.getContents() != null && !dto.getContents().isEmpty()) {
                log.info("공지사항 내용: {}", dto.getContents());
                spec = spec.and((root1, query1, cb) -> cb.like(root.get("contents"), "%" + dto.getContents() + "%"));
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
