package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.event.EventDTO;
import com.lab.darackbang.dto.event.EventSearchDTO;
import com.lab.darackbang.service.event.EventService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("admin/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    // 이벤트 등록
    @PostMapping("/")
    public Map<String,String> register(EventDTO eventDTO, MultipartFile file) throws IOException {
        log.info("이벤트 등록 시작 -----------------------");
        return eventService.create(eventDTO, file);
    }

    // 이벤트 리스트 출력
    @GetMapping("/list")
    public PageDTO<EventDTO, EventSearchDTO> list(@ModelAttribute EventSearchDTO eventSearchDTO,
                                                  @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session) {
        log.info("이벤트 리스트 출력 시작 ---------------------");
        return eventService.findAll(eventSearchDTO, pageable);
    }

}
