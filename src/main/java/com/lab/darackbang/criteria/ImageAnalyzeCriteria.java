package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.analyze.ImageAnalyzeSearchDTO;
import com.lab.darackbang.entity.ImageAnalyze;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
@Slf4j
public class ImageAnalyzeCriteria {

    public static Specification<ImageAnalyze> byCriteria(ImageAnalyzeSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {

            Specification<ImageAnalyze> spec = Specification.where(null);

            // userEmail 필터 추가
            if (dto.getFileName() != null && !dto.getFileName().isEmpty()) {
                log.info("fileName: {}", dto.getFileName());
                spec = spec.and((root1, query1, cb) -> cb.like(root1.get("fileName"), "%" + dto.getFileName() + "%"));
            }

            Objects.requireNonNull(query).distinct(true);
            //검색 필터 조건이 있으면 아래 추가함.

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
