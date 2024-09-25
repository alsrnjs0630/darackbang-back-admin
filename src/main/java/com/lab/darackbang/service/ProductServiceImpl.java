package com.lab.darackbang.service;

import com.lab.darackbang.criteria.ProductCriteria;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.entity.Product;
import com.lab.darackbang.entity.ProductImage;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.mapper.ProductMapper;
import com.lab.darackbang.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final PageMapper pageMapper;


    @Override
    public Map<String, String> create(ProductDTO productDTO) {

        productDTO.setIsVisible(Boolean.TRUE);
        productDTO.setIsSoldout(Boolean.FALSE);
        productDTO.setIsGmo(Boolean.FALSE);
        productDTO.setIsDeleted(Boolean.FALSE);
        productDTO.setWishCount(0);

        Product product = productMapper.toEntity(productDTO);

        List<ProductImage> productImages = Optional.ofNullable(productDTO.getProductImageFiles())
                .map(List::stream)  //List를 stream으로 변환
                .orElseGet(Stream::empty)  // productImageFiles가 null이면 빈 스트림 반환
                //각 MultipartFile 객체(file)를 ProductImage 객체로 변환하는 로직을 스트림에서 처리
                .map(file -> {      //getOriginalFilename()을 사용하기 때문에 file도 MultipartFile의 객체라고 인식
                    ProductImage productImage = new ProductImage();
                    productImage.setProductFileName(file.getOriginalFilename());
                    productImage.setProduct(product);
                    return productImage;
                })
                .collect(Collectors.toList());

        product.setProductImages(productImages);

        productRepository.save(product);

        return Map.of("RESULT", "SUCCESS");
    }

    /**
     * 주어진 검색 조건 및 페이징 정보를 기반으로 모든 제품 목록을 조회합니다.
     *
     * @param searchDTO 제품 검색 조건을 담고 있는 DTO
     * @param pageable 페이징 처리를 위한 Pageable 객체
     * @return 검색 조건에 맞는 제품 목록을 페이지 단위로 반환
     */
    @Override
    @Transactional(readOnly = true)
    public PageDTO<ProductDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable) {
        // ProductSearchDTO에 기반하여 Specification<Product> 객체 생성
        Specification<Product> spec = ProductCriteria.byCriteria(searchDTO);

        //페이지 번호(프론트에서는 1부터 시작하지만 실제로는 현재 페이지번호 -1)
        int pageNumber = (pageable.getPageNumber() < 1) ? 0 : pageable.getPageNumber() - 1;
        Pageable correctedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());

        // JPA 리포지토리를 사용하여 페이징을 적용한 상품 목록 조회 후, ProductMapper를 통해 ProductDTO로 변환
        return pageMapper.toDTO(productRepository.findAll(spec, correctedPageable).map(productMapper::toDTO));
    }
}

