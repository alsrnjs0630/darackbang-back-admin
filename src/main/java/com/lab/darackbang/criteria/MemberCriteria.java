package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.member.MemberSearchDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

/**
 * ProductCriteria 클래스는 ProductSearchDTO 엔티티에 대한 검색 조건(Specification)을 정의하는 클래스입니다.
 * 이 클래스는 동적 쿼리를 생성하는 데 사용되며, 다양한 검색 필터를 제공하여
 * 데이터베이스에서 원하는 조건에 맞는 데이터를 조회할 수 있도록 합니다.
 */
@Slf4j
public class MemberCriteria {

    public static Specification<Member> byCriteria(MemberSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {

            Specification<Member> spec = Specification.where(null);

            // userEmail 필터 추가
            if (dto.getUserEmail() != null && !dto.getUserEmail().isEmpty()) {
                log.info("userEmail: {}", dto.getUserEmail());
                spec = spec.and((root1, query1, cb) -> cb.like(root1.get("userEmail"), "%" + dto.getUserEmail() + "%"));
            }


            //사용자 이름
            if (dto.getName() != null && !dto.getName().isEmpty()) {
                log.info("name: {}", dto.getName());
                spec = spec.and((root1, query1, cb) -> cb.like(root1.get("name"), "%" + dto.getName() + "%"));
            }

            // 사용자 본번호
            if (dto.getPhoneNo() != null && !dto.getPhoneNo().isEmpty()) {
                log.info("phoneNo: {}", dto.getPhoneNo());
                spec = spec.and((root1, query1, cb) -> cb.like(root1.get("phoneNo"), "%" + dto.getPhoneNo() + "%"));
            }


            //사용사 상태
            if (dto.getMemberState() != null && !dto.getMemberState().isEmpty()) {
                log.info("memberState: {}", dto.getMemberState());
                spec = spec.and((root1, query1, cb) -> cb.like(root1.get("memberState"), "%" + dto.getMemberState() + "%"));
            }

            //사용자 블랙리스트 처리
            if (dto.getIsBlacklist() != null) {
                log.info("blackList: {}", dto.getIsBlacklist());
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("isBlacklist"), dto.getIsBlacklist()));
            }

            //사용자 성별 gender 필터 추가
            if (dto.getGender() != null && !dto.getGender().isEmpty()) {
                log.info("gender: {}", dto.getGender());
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("gender"), dto.getGender()));
            }

            //검색 필터 조건이 있으면 아래 추가함.

            //삭제 처리된 상품은 조회 대상에서 제외
            //spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("isDeleted"),false));

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
