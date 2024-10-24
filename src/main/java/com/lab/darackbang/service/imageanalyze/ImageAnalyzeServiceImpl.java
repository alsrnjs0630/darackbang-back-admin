package com.lab.darackbang.service.imageanalyze;

import com.lab.darackbang.common.utils.ImageAnalyzeUtil;
import com.lab.darackbang.criteria.ImageAnalyzeCriteria;
import com.lab.darackbang.dto.analyze.ImageAnalyzeDTO;
import com.lab.darackbang.dto.analyze.ImageAnalyzeSearchDTO;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.entity.ImageAnalyze;
import com.lab.darackbang.exception.NotFoundException;
import com.lab.darackbang.mapper.ImageAnalyzeMapper;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.repository.ImageAnalyzeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class ImageAnalyzeServiceImpl implements ImageAnalyzeService{

    private final ImageAnalyzeRepository imageAnalyzeRepository;

    private final ImageAnalyzeMapper imageAnalyzeMapper;

    private final PageMapper pageMapper;

    private final ImageAnalyzeUtil imageAnalyzeUtil;

    @Override
    public PageDTO<ImageAnalyzeDTO, ImageAnalyzeSearchDTO> findAll(ImageAnalyzeSearchDTO searchDTO, Pageable pageable) {
        Specification<ImageAnalyze> spec = ImageAnalyzeCriteria.byCriteria(searchDTO);

        //페이지 번호(프론트에서는 1부터 시작하지만 실제로는 현재 페이지번호 -1)
        int pageNumber = (pageable.getPageNumber() < 1) ? 0 : pageable.getPageNumber() - 1;

        Pageable correctedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());

        // JPA 리포지토리를 사용하여 페이징을 적용한 상품 목록 조회 후, ProductMapper를 통해 ProductDTO로 변환
        return pageMapper.toDTO(imageAnalyzeRepository.findAll(spec, correctedPageable).map(imageAnalyzeMapper::toDTO), searchDTO);
    }

    @Override
    public ImageAnalyzeDTO findOne(Long id) {
        return imageAnalyzeRepository.findById(id).map(imageAnalyzeMapper::toDTO).orElseThrow(NotFoundException::new);
    }

    @Override
    public ResponseEntity<Resource> getFile(String fileName) {
        return imageAnalyzeUtil.getFile(fileName);
    }
}
