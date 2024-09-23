package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.common.CommonGroupCodeDTO;
import com.lab.darackbang.entity.CommonGroupCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommonGroupCodeMapper {

    CommonGroupCodeDTO toDTO(CommonGroupCode commonGroupCode);

    CommonGroupCode toEntity(CommonGroupCodeDTO commonGroupCodeDTO);
}
