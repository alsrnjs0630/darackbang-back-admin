package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.notice.NoticeDTO;
import com.lab.darackbang.dto.notice.NoticeSearchDTO;
import com.lab.darackbang.service.notice.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/notices")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public PageDTO<NoticeDTO, NoticeSearchDTO> getList(@ModelAttribute NoticeSearchDTO noticeSearchDTO,
                                                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session) {
        return noticeService.findAll(noticeSearchDTO, pageable);
    }

}
