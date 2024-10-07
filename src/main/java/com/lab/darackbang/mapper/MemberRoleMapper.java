package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.member.MemberRoleDTO;
import com.lab.darackbang.entity.MemberRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberRoleMapper {


    MemberRoleDTO toDTO(MemberRole memberRole);


    @Mapping(target = "member" , ignore = true)
    MemberRole toEntity(MemberRoleDTO memberRoleDTO);

}
