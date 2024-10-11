package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.member.MemberReqDTO;
import com.lab.darackbang.entity.Member;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface MemberReqMapper {

    MemberReqMapper INSTANCE = Mappers.getMapper(MemberReqMapper.class);

    MemberReqDTO toDTO(Member member);

    @Mapping(target = "memberRoles", ignore = true)
    @Mapping(target = "memberCard", ignore = true)
    @Mapping(target = "subscribes", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "qandas", ignore = true)
    @Mapping(target = "wishlists", ignore = true)
    @Mapping(target = "productReviews", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Member toEntity(MemberReqDTO memberReqDTO);

}