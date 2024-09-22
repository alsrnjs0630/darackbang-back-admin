package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.entity.Product;
import org.springframework.data.jpa.domain.Specification;

/**
 * ProductCriteria 클래스는 ProductSearchDTO 엔티티에 대한 검색 조건(Specification)을 정의하는 클래스입니다.
 * 이 클래스는 동적 쿼리를 생성하는 데 사용되며, 다양한 검색 필터를 제공하여
 * 데이터베이스에서 원하는 조건에 맞는 데이터를 조회할 수 있도록 합니다.
 */
public class ProductCriteria {

    public static Specification<Product> byCriteria(ProductSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {
            Specification<Product> spec = Specification.where(null);

            // productName 필터 추가
            if (dto.getProductName() != null && !dto.getProductName().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> cb.like(root1.get("productName"), "%" + dto.getProductName() + "%"));
            }

            // SalePrice 필터 추가
            if (dto.getSalePrice() != null) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("salePrice"), dto.getSalePrice()));
            }

            //검색 필터 조건이 있으면 아래 추가함.

            //삭제 처리된 상품은 조회 대상에서 제외
            spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("isDeleted"),false));

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
