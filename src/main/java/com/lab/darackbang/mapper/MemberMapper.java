package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.entity.Member;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = MemberRoleMapper.class)
public interface MemberMapper {

    MemberDTO toDTO(Member member);

    @Mapping(target = "memberRoles", ignore = true)
    @Mapping(target = "memberCard", ignore = true)
    @Mapping(target = "subscribes", ignore = true)
    @Mapping(target = "orderHistories", ignore = true)
    @Mapping(target = "carts", ignore = true)
    @Mapping(target = "qandas", ignore = true)
    @Mapping(target = "wishlists", ignore = true)
    @Mapping(target = "productReviews", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Member toEntity(MemberDTO memberDTO);


    @AfterMapping
    default void setDefaultValues(@MappingTarget MemberDTO memberDTO) {
        if (memberDTO.getGender().equals("F")) {
            memberDTO.setGender("여자");
        } else {
            memberDTO.setGender("남자");
        }

        if (memberDTO.getMemberState().equals("01")) {
            memberDTO.setMemberState("활동중");
        } else if(memberDTO.getMemberState().equals("02")){
            memberDTO.setMemberState("탈퇴");
        }else{
            memberDTO.setMemberState("탈퇴대기");
        }
    }
}
