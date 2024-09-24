package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.subscribe.SubscribeSearchDTO;
import com.lab.darackbang.entity.Subscribe;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
/**
 * SubscribeCriteria 클래스는 {@link SubscribeSearchDTO} 엔티티에 대한 검색 조건(Specification)을 정의하는 클래스입니다.
 * 이 클래스는 동적 쿼리를 생성하는 데 사용되며, 다양한 검색 필터를 제공하여
 * 데이터베이스에서 원하는 조건에 맞는 데이터를 조회할 수 있도록 합니다.
 */
public class SubscribeCriteria {
    /**
     * 주어진 {@link SubscribeSearchDTO}를 기반으로 {@link Specification}을 생성하여 구독 데이터를 필터링합니다.
     *
     * @param dto 검색 조건이 담긴 SubscribeSearchDTO 객체
     * @return 구독 데이터를 필터링하기 위한 Specification 객체
     */
    public static Specification<Subscribe> byCriteria(SubscribeSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {

            Specification<Subscribe> spec = Specification.where(null);

            // 상품명 필터 추가
            // Subscribe 엔티티에 직접 상품명이 없으므로 Product 엔티티와 조인(Join)을 해야 함.
            // Product 엔티티의 productName 필드를 사용해 상품명을 기준으로 필터링.
            if (dto.getProductName() != null && !dto.getProductName().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> {
                    // Subscribe 엔티티와 Product 엔티티를 Join하여 productName 필드에 접근
                    Join<Object, Object> productJoin = root1.join("product", JoinType.INNER);
                    // productName이 검색 키워드를 포함하는지 검사 (LIKE 쿼리 사용)
                    return cb.like(productJoin.get("productName"), "%" + dto.getProductName() + "%");
                });
            }

            // 구독 ID로 필터 추가
            // 구독 ID(dto.getId())가 null이 아닌 경우, 해당 ID로 필터링
            if (dto.getId() != null) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("id"), dto.getId()));
            }

            // 구독 수량(subQuantity)으로 필터 추가
            // 구매 수량(dto.getSubQuantity())이 null이 아닌 경우, 해당 수량으로 필터링
            if (dto.getSubQuantity() != null) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("subQuantity"), dto.getSubQuantity()));
            }

            // 구독 가격(subPrice)으로 필터 추가
            // 구독 가격(dto.getSubPrice())가 null이 아닌 경우, 해당 가격으로 필터링
            if (dto.getSubPrice() != null) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("subPrice"), dto.getSubPrice()));
            }

            // 구독 상태(subState)로 필터 추가
            // 구독 상태(dto.getSubState())가 null이 아니고 비어 있지 않은 경우, 해당 상태로 필터링
            if (dto.getSubState() != null && !dto.getSubState().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("subState"), dto.getSubState()));
            }

            // 최종적으로 생성된 필터 조건을 반환
            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
