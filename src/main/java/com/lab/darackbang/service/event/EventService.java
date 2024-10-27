package com.lab.darackbang.service.event;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface EventService {
    Map<String,String> create(EventDTO eventDTO, MultipartFile file) throws IOException;
    PageDTO<EventDTO, EventSearchDTO> findAll(EventSearchDTO searchDTO, Pageable pageable);
    EventDTO read(Long id);
    ResponseEntity<Resource> getFile(String fileName);
    Map<String,String> update(EventDTO eventDTO, MultipartFile file) throws IOException;
}
