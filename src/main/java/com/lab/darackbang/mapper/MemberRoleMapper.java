package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.member.MemberRoleDTO;
import com.lab.darackbang.entity.MemberRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberRoleMapper {

    MemberRoleDTO toDTO(MemberRole memberRole);

    @Mapping(source = "member", target = "member")
    @Mapping(target = "member.memberCard", ignore = true)
    @Mapping(target = "member.subscribes", ignore = true)
    @Mapping(target = "member.orderHistories", ignore = true)
    @Mapping(target = "member.carts", ignore = true)
    @Mapping(target = "member.qandas", ignore = true)
    @Mapping(target = "member.wishlists", ignore = true)
    @Mapping(target = "member.productReviews", ignore = true)
    @Mapping(target = "member.payments", ignore = true)
    MemberRole toEntity(MemberRoleDTO memberRoleDTO);
}
