package com.lab.darackbang.controller;

import com.lab.darackbang.dto.analyze.ImageAnalyzeDTO;
import com.lab.darackbang.dto.analyze.ImageAnalyzeSearchDTO;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.service.imageanalyze.ImageAnalyzeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("admin/analyzes")
public class ImageAnalyzeController {

    private final ImageAnalyzeService imageAnalyzeService;

    @GetMapping("/list")
    public PageDTO<ImageAnalyzeDTO, ImageAnalyzeSearchDTO> list(@ModelAttribute ImageAnalyzeSearchDTO searchDTO,
                                                                @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("이미지 분석 리스트");

        return imageAnalyzeService.findAll(searchDTO, pageable);
    }

    @GetMapping("/{id}")
    public ImageAnalyzeDTO get(@PathVariable Long id) {
        return imageAnalyzeService.findOne(id);
    }

    /**
     * 상품이미지 보기
     *
     * @param filename
     * @return
     * @throws IOException
     */
    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) throws IOException {
        return imageAnalyzeService.getFile(filename);
    }
}
