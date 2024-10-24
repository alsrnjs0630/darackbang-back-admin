package com.lab.darackbang.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.darackbang.common.utils.CustomFileUtil;
import com.lab.darackbang.common.utils.ImageAnalyzeUtil;
import com.lab.darackbang.criteria.ProductCriteria;
import com.lab.darackbang.dto.common.FileInfoDTO;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductReqDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.entity.Product;
import com.lab.darackbang.entity.ProductImage;
import com.lab.darackbang.exception.ProductNotFoundException;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.mapper.ProductMapper;
import com.lab.darackbang.mapper.ProductReqMapper;
import com.lab.darackbang.repository.ImageAnalyzeRepository;
import com.lab.darackbang.repository.ProductImageRepository;
import com.lab.darackbang.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final ProductMapper productMapper;

    private final ProductReqMapper productReqMapper;

    private final PageMapper pageMapper;

    private final CustomFileUtil customFileUtil;

    private final ImageAnalyzeUtil imageAnalyzeUtil;

    private final ImageAnalyzeRepository imageAnalyzeRepository;


    @Override
    public Map<String, String> create(ProductDTO productDTO, List<MultipartFile> files, List<MultipartFile> descFiles) throws IOException {

        Optional<Product> findProduct = productRepository.findTopByOrderByPnoDesc();

        String pno = findProduct.map(product -> genPno(product.getPno())).orElse("TP0000000001");

        Product product = productMapper.toEntity(productDTO);
        product.setPno(pno);

        List<ProductImage> imageList = new ArrayList<>();
        imageList.addAll(createProductImages(customFileUtil.saveProductInfoFiles(files), product, "INFO"));
        imageList.addAll(createProductImages(customFileUtil.saveProductDescFiles(descFiles), product, "DESC"));

        product.setProductImages(imageList);
        productRepository.save(product);

        //이미지 분석 모듈 추가
        Optional.ofNullable(imageAnalyzeUtil.imageAnalyze(files, descFiles))
                .ifPresent(imageAnalyzeRepository::saveAll);

        return Map.of("RESULT", "SUCCESS");
    }
    /**
     * 주어진 검색 조건 및 페이징 정보를 기반으로 모든 제품 목록을 조회합니다.
     *
     * @param searchDTO 제품 검색 조건을 담고 있는 DTO
     * @param pageable  페이징 처리를 위한 Pageable 객체
     * @return 검색 조건에 맞는 제품 목록을 페이지 단위로 반환
     */
    @Override
    @Transactional(readOnly = true)
    public PageDTO<ProductDTO, ProductSearchDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable) {
        // ProductSearchDTO에 기반하여 Specification<Product> 객체 생성

        log.info(searchDTO.toString());

        Specification<Product> spec = ProductCriteria.byCriteria(searchDTO);

        //페이지 번호(프론트에서는 1부터 시작하지만 실제로는 현재 페이지번호 -1)
        int pageNumber = (pageable.getPageNumber() < 1) ? 0 : pageable.getPageNumber() - 1;
        Pageable correctedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());

        // JPA 리포지토리를 사용하여 페이징을 적용한 상품 목록 조회 후, ProductMapper를 통해 ProductDTO로 변환
        return pageMapper.toDTO(productRepository.findAll(spec, correctedPageable).map(productMapper::toDTO), searchDTO);
    }

    @Override
    public ProductDTO findOne(Long id) {
        return productRepository.findById(id).map(productMapper::toDTO).orElse(null);
    }

    @Override
    public Map<String, String> delete(Long id) {
        return productRepository.findById(id).map(product -> {
            product.setIsDeleted(true);
            productRepository.save(product);
            return Map.of("RESULT", "SUCCESS");
        }).orElseThrow(ProductNotFoundException::new);
    }


    @Override
    public Map<String, String> active(Long id) {
        return productRepository.findById(id).map(product -> {
            product.setIsDeleted(false);
            productRepository.save(product);
            return Map.of("RESULT", "SUCCESS");
        }).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public ResponseEntity<Resource> getFile(String fileName) {
        return customFileUtil.getFile(fileName);
    }

    @Override
    public Map<String, String> update(ProductReqDTO productReqDTO, List<MultipartFile> files, List<MultipartFile> descFiles) throws IOException {
        log.info("Updating product with ID: {}", productReqDTO.getId());


        ObjectMapper objectMapper = new ObjectMapper();
        List<FileInfoDTO> productImages = null;

        try {
            productImages = objectMapper.readValue(productReqDTO.getProductImages(), new TypeReference<>() {
            });

            System.out.println(productImages);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Retrieve and map product entity

        Product product = productRepository.findById(productReqDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        //객체 복사
        BeanUtils.copyProperties(productReqDTO, product, "productImages", "id");

        // Update existing images
        List<ProductImage> existingImages = product.getProductImages().stream()
                .filter(image -> !image.getIsDeleted())
                .collect(Collectors.toList());

        //프론트에서 삭제한 이미지 isDelete 처리와 순서 변경한 이미지 sortNum 재설정
        updateOrDeleteExistingImages(existingImages, productImages);


        //프론트에서 새로 추가한 이미지 저장및 순서에 맞는 sortNum 설정
        List<ProductImage> newProductImages = new ArrayList<>();

        if (files != null) {

            newProductImages.addAll(updateNewProductImagesFromFiles(files, product, "INFO"));
        }

        if (descFiles != null) {
            newProductImages.addAll(updateNewProductImagesFromFiles(descFiles, product, "DESC"));
        }

        if (!newProductImages.isEmpty()) {
            existingImages.addAll(newProductImages);
        }

        product.setProductImages(existingImages);

        productRepository.save(product);

        Optional.ofNullable(imageAnalyzeUtil.imageAnalyze(files, descFiles))
                .ifPresent(imageAnalyzeRepository::saveAll);

        return Map.of("RESULT", "SUCCESS");
    }


    // Handle update or deletion of existing images
    private void updateOrDeleteExistingImages(List<ProductImage> existingImages, List<FileInfoDTO> infoDTOList) {
        existingImages.forEach(image -> {
            infoDTOList.stream()
                    .filter(dto -> dto.getFileName().equals(image.getProductFileName()))
                    .findFirst()
                    .ifPresentOrElse(
                            dto -> image.setSortNum(dto.getSortNum()),
                            () -> image.setIsDeleted(true)
                    );
        });
    }

    // Handle new product images creation from files
    private List<ProductImage> updateNewProductImagesFromFiles(List<MultipartFile> files, Product product, String type) throws IOException {
        return updateCreateProductImages(customFileUtil.updateNewProductInfoFiles(files), product, type);
    }


    // 반복된 코드 처리 메서드로 분리
    private List<ProductImage> createProductImages(List<String> fileNames, Product product, String type) {
        AtomicInteger sortNum = new AtomicInteger(1);
        return fileNames.stream()
                .map(fileName -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setProductType(type);
                    productImage.setSortNum(sortNum.getAndIncrement());
                    productImage.setProductFileName(fileName);
                    productImage.setProduct(product);
                    productImage.setIsDeleted(false);
                    return productImage;
                })
                .collect(Collectors.toList());
    }

    // 반복된 코드 처리 메서드로 분리
    private List<ProductImage> updateCreateProductImages(List<FileInfoDTO> fileNames, Product product, String type) {
        AtomicInteger sortNum = new AtomicInteger(1);
        return fileNames.stream()
                .map(fileName -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setProductType(type);
                    productImage.setSortNum(fileName.getSortNum());
                    productImage.setProductFileName(fileName.getFileName());
                    productImage.setProduct(product);
                    productImage.setIsDeleted(false);
                    return productImage;
                })
                .collect(Collectors.toList());
    }


    private String genPno(String pno) {

        if (pno != null) {

            if (pno.startsWith("TP")) {
                String numberPart = pno.substring(2); // "TP"를 제거
                long number = Long.parseLong(numberPart); // 문자열을 숫자로 변환
                number++; // 1 증가

                // 10자리 문자열로 변환
                String result = String.format("TP%010d", number);
                return result;
            }
        } else {
            return "TP0000000001";
        }

        return "TP0000000001";
    }


    private String extractSequenceNumber(String fileName) {
        // 정규표현식: 파일명의 시작에서 숫자와 언더스코어(_)로 시작하는 패턴을 찾음
        String regex = "^(\\d+)_";  // 파일명의 시작(^)에서 숫자(\\d+)와 언더스코어(_)를 포함한 패턴

        // Pattern과 Matcher를 사용하여 파일명에서 일치하는 패턴을 찾음
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);

        // 패턴이 파일명에서 일치하면 숫자 부분을 반환
        if (matcher.find()) {
            return matcher.group(1);  // 첫 번째 캡처 그룹 (숫자)만 반환
        } else {
            return null;  // 패턴이 없을 경우 null 반환
        }
    }


}

