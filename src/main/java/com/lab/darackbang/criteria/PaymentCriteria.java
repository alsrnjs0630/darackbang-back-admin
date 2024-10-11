package com.lab.darackbang.criteria;


import com.lab.darackbang.dto.payment.PaymentSearchDTO;
import com.lab.darackbang.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

/**
 * ProductCriteria 클래스는 ProductSearchDTO 엔티티에 대한 검색 조건(Specification)을 정의하는 클래스입니다.
 * 이 클래스는 동적 쿼리를 생성하는 데 사용되며, 다양한 검색 필터를 제공하여
 * 데이터베이스에서 원하는 조건에 맞는 데이터를 조회할 수 있도록 합니다.
 */
@Slf4j
public class PaymentCriteria {

    public static Specification<Payment> byCriteria(PaymentSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {

            Specification<Payment> spec = Specification.where(null);

            if (dto.getPgProvider() != null) {
                log.info("payMethod: {}", dto.getPgProvider());
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("pgProvider"),  dto.getPgProvider() ));
            }

            // userEmail 필터 추가
            if (dto.getBuyerName() != null && !dto.getBuyerName().isEmpty()) {
                log.info("buyerName: {}", dto.getBuyerName());
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("buyerName"),  dto.getBuyerName() ));
            }

            //사용자 이름
            if (dto.getSuccess() != null) {
                log.info("name: {}", dto.getSuccess());
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("success"),  dto.getSuccess() ));
            }

            //사용자 이름
            if (dto.getPaidAmount() != null) {
                log.info("paidAmount: {}", dto.getPaidAmount());
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("paidAmount"),  dto.getPaidAmount() ));
            }

            //검색 필터 조건이 있으면 아래 추가함.

            //삭제 처리된 상품은 조회 대상에서 제외
            //spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("isDeleted"),false));

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
