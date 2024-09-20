package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.CommonCodeDTO;
import com.lab.darackbang.entity.CommonCode;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommonCodeMapper {

    CommonCodeDTO toDTO(CommonCode commonCode);

    CommonCode toEntity(CommonCodeDTO commonCodeDTO);

    @AfterMapping
    default void afterMapping(@MappingTarget CommonCodeDTO commonCodeDTO, CommonCode commonCode) {
        if (commonCode.getIsUsed()) {
            commonCodeDTO.setIsUsed("사용중");
        }else{
            commonCodeDTO.setIsUsed("비사용");
        }
    }

    @AfterMapping
    default void afterMapping(@MappingTarget CommonCode commonCode, CommonCodeDTO commonCodeDTO) {
        commonCode.setIsUsed(!commonCodeDTO.getIsUsed().equals("비사용"));
    }

}
