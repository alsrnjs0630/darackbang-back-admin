package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.CommonCodeDTO;
import com.lab.darackbang.entity.CommonCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommonCodeMapper {

    CommonCodeDTO toDTO(CommonCode commonCode);

    CommonCode toEntity(CommonCodeDTO commonCodeDTO);
}
