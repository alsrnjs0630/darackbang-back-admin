package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.subscribe.SubscribeDTO;
import com.lab.darackbang.entity.Subscribe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscribeMapper {

    SubscribeDTO toDTO(Subscribe subscribe);
}
