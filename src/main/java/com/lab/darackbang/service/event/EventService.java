package com.lab.darackbang.service.event;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface EventService {
    Map<String,String> create(EventDTO eventDTO, MultipartFile file) throws IOException;
    PageDTO<EventDTO, EventSearchDTO> findAll(EventSearchDTO searchDTO, Pageable pageable);
}
