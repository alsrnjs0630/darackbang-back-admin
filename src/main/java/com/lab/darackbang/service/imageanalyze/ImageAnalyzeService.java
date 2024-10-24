package com.lab.darackbang.service.imageanalyze;

import com.lab.darackbang.dto.analyze.ImageAnalyzeDTO;
import com.lab.darackbang.dto.analyze.ImageAnalyzeSearchDTO;
import com.lab.darackbang.dto.common.PageDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ImageAnalyzeService {

    PageDTO<ImageAnalyzeDTO, ImageAnalyzeSearchDTO> findAll(ImageAnalyzeSearchDTO searchDTO, Pageable pageable);

    ImageAnalyzeDTO findOne(Long id);

    ResponseEntity<Resource> getFile(String fileName);


}
