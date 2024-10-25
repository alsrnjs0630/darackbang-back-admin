package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDTO toDTO(Event event);

    Event toEntity(EventDTO dto);
}
