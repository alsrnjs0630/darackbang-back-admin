package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.member.MemberSearchDTO;
import com.lab.darackbang.dto.order.OrderSearchDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Order;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

/**
 * ProductCriteria 클래스는 ProductSearchDTO 엔티티에 대한 검색 조건(Specification)을 정의하는 클래스입니다.
 * 이 클래스는 동적 쿼리를 생성하는 데 사용되며, 다양한 검색 필터를 제공하여
 * 데이터베이스에서 원하는 조건에 맞는 데이터를 조회할 수 있도록 합니다.
 */
@Slf4j
public class OrderCriteria {

    public static Specification<Order> byCriteria(OrderSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {

            Specification<Order> spec = Specification.where(null);

            // userEmail 필터 추가
            if (dto.getUserEmail() != null && !dto.getUserEmail().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> {
                    // Subscribe 엔티티와 Product 엔티티를 Join하여 productName 필드에 접근
                    Join<Object, Object> memberJoin = root1.join("member", JoinType.INNER);
                    // productName이 검색 키워드를 포함하는지 검사 (LIKE 쿼리 사용)
                    return cb.like(memberJoin.get("userEmail"), "%" + dto.getUserEmail() + "%");
                });
            }

            if (dto.getName() != null && !dto.getName().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> {
                    // Subscribe 엔티티와 Product 엔티티를 Join하여 productName 필드에 접근
                    Join<Object, Object> memberJoin = root1.join("member", JoinType.INNER);
                    // productName이 검색 키워드를 포함하는지 검사 (LIKE 쿼리 사용)
                    return cb.like(memberJoin.get("name"), "%" + dto.getName() + "%");
                });
            }


            //사용자 이름
            if (dto.getTotalOrderPrice() != null) {
                log.info("name: {}", dto.getTotalOrderPrice());
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("totalOrderPrice"),  dto.getTotalOrderPrice() ));
            }

            //검색 필터 조건이 있으면 아래 추가함.

            //삭제 처리된 상품은 조회 대상에서 제외
            //spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("isDeleted"),false));

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
