package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.subscribe.SubscribeSearchDTO;
import com.lab.darackbang.entity.Subscribe;
import org.springframework.data.jpa.domain.Specification;

/**
 * SubscribeCriteria 클래스는 SubscribeSearchDTO 엔티티에 대한 검색 조건(Specification)을 정의하는 클래스입니다.
 * 이 클래스는 동적 쿼리를 생성하는 데 사용되며, 다양한 검색 필터를 제공하여
 * 데이터베이스에서 원하는 조건에 맞는 데이터를 조회할 수 있도록 합니다.
 */

public class SubscribeCriteria {
    public static Specification<Subscribe> byCriteria(SubscribeSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {
            Specification<Subscribe> spec = Specification.where(null);

            // productName 필터 추가
            if (dto.getProductName() != null && !dto.getProductName().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> cb.like(root1.get("productName"), "%" + dto.getProductName() + "%"));
            }

            // 구독아이디로 필터 추가
            if (dto.getId() != null) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("id"), dto.getId()));
            }

            // subQuantity 필터 추가
            if (dto.getSubQuantity() != null) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("subQuantity"), dto.getSubQuantity()));
            }

            // subPrice 필터 추가
            if (dto.getSubPrice() != null) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("subPrice"), dto.getSubPrice()));
            }

            // subState 필터 추가
            if (dto.getSubState() != null && !dto.getSubState().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("subState"), dto.getSubState()));
            }

            return spec.toPredicate(root, query, criteriaBuilder);

        };
    }
}
