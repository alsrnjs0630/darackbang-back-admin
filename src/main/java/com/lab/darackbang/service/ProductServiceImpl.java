package com.lab.darackbang.service;

import com.lab.darackbang.common.utils.ImageUtil;
import com.lab.darackbang.criteria.ProductCriteria;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.entity.Product;
import com.lab.darackbang.entity.ProductImage;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.mapper.ProductMapper;
import com.lab.darackbang.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final PageMapper pageMapper;

    private final ImageUtil imageUtil;

    //상품 등록
    @Override
    public Map<String, String> create(ProductDTO productDTO) throws IOException {

        log.info("상품 등록 시작");

        //상품번호 생성
        String pno =null;

        Optional<Product> findProduct = productRepository.findTopByOrderByPnoDesc();

        if(findProduct.isPresent()){
            pno = genPno(findProduct.get().getPno());
        }else{
            pno = "TP0000000001";
        }

        // 상품 디폴트값 설정
        productDTO.setPno(pno);
        productDTO.setIsVisible(Boolean.TRUE);
        productDTO.setIsSoldout(Boolean.FALSE);
        productDTO.setIsGmo(Boolean.FALSE);
        productDTO.setIsDeleted(Boolean.FALSE);
        productDTO.setWishCount(0);

        // 사용자가 입력한 값(DTO) 엔티티로 변환
        Product product = productMapper.toEntity(productDTO);

        List<String> uploadFileNames = imageUtil.saveImages("product", productDTO.getProductImageFiles());

        List<ProductImage> productImageList = new ArrayList<>();

        uploadFileNames.forEach(imageFileName -> {
            ProductImage productImage = new ProductImage();
            productImage.setProductFileName(imageFileName);
            productImage.setProduct(product);
            productImageList.add(productImage);
        });

        product.setProductImages(productImageList);

        log.info("상품 등록 끝");

        productRepository.save(product);

        return Map.of("RESULT", "SUCCESS");
    }

    //상품번호 생성 메서드
    String genPno(String pno) {

        if(pno != null) {

            if (pno.startsWith("TP")) {
                String numberPart = pno.substring(2); // "TP"를 제거
                long number = Long.parseLong(numberPart); // 문자열을 숫자로 변환
                number++; // 1 증가

                // 10자리 문자열로 변환
                String result = String.format("TP%010d", number);
                return result;
            }
        } else{
            return "TP0000000001";
        }

        return "TP0000000001";
    }

    //업로드한 이미지 조회
    @Override
    public ResponseEntity<Resource> getProductImage(String imageName) {
        return imageUtil.getImage("product", imageName);
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

    //상품 수정
    @Override
    public Map<String, String> update(String pno, ProductDTO productDTO) throws IOException {

        log.info("상품 수정 시작, pno: {}", pno);

        // 해당 ID의 상품을 찾기
        Product existingProduct = productRepository.findByPno(pno)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        // 입력된 값이 있는 경우에만 업데이트
        //상품을 실제로 수정할 때 null이 아닌 값은 이미 입력돼있으므로
        //포스트맨에서 상품 수정 시 nullable=false인 칼럼들에도 값을 넣어준 후 수정해야한다.
        if (!Objects.equals(existingProduct.getPno(), productDTO.getPno())) {
            existingProduct.setPno(productDTO.getPno());
        }
        if (!Objects.equals(existingProduct.getProductName(), productDTO.getProductName())) {
            existingProduct.setProductName(productDTO.getProductName());
        }
        if (!Objects.equals(existingProduct.getProductDetail(), productDTO.getProductDetail())) {
            existingProduct.setProductDetail(productDTO.getProductDetail());
        }
        if (!Objects.equals(existingProduct.getRetailPrice(), productDTO.getRetailPrice())) {
            existingProduct.setRetailPrice(productDTO.getRetailPrice());
        }
        if (!Objects.equals(existingProduct.getSalePrice(), productDTO.getSalePrice())) {
            existingProduct.setSalePrice(productDTO.getSalePrice());
        }
        if (!Objects.equals(existingProduct.getType(), productDTO.getType())) {
            existingProduct.setType(productDTO.getType());
        }
        if (!Objects.equals(existingProduct.getCaution(), productDTO.getCaution())) {
            existingProduct.setCaution(productDTO.getCaution());
        }
        if (!Objects.equals(existingProduct.getIsVisible(), productDTO.getIsVisible())) {
            existingProduct.setIsVisible(productDTO.getIsVisible());
        }
        if (!Objects.equals(existingProduct.getManufacture(), productDTO.getManufacture())) {
            existingProduct.setManufacture(productDTO.getManufacture());
        }
        if (!Objects.equals(existingProduct.getBrand(), productDTO.getBrand())) {
            existingProduct.setBrand(productDTO.getBrand());
        }
        if (!Objects.equals(existingProduct.getOrigin(), productDTO.getOrigin())) {
            existingProduct.setOrigin(productDTO.getOrigin());
        }
        if (!Objects.equals(existingProduct.getMaterial(), productDTO.getMaterial())) {
            existingProduct.setMaterial(productDTO.getMaterial());
        }
        if (!Objects.equals(existingProduct.getCategory(), productDTO.getCategory())) {
            existingProduct.setCategory(productDTO.getCategory());
        }
        if (!Objects.equals(existingProduct.getSaleCompany(), productDTO.getSaleCompany())) {
            existingProduct.setSaleCompany(productDTO.getSaleCompany());
        }
        if (!Objects.equals(existingProduct.getSaleCompanyInfo(), productDTO.getSaleCompanyInfo())) {
            existingProduct.setSaleCompanyInfo(productDTO.getSaleCompanyInfo());
        }
        if (!Objects.equals(existingProduct.getIsDeleted(), productDTO.getIsDeleted())) {
            existingProduct.setIsDeleted(productDTO.getIsDeleted());
        }
        if (!Objects.equals(existingProduct.getIsSoldout(), productDTO.getIsSoldout())) {
            existingProduct.setIsSoldout(productDTO.getIsSoldout());
        }
        if (!Objects.equals(existingProduct.getNutrition(), productDTO.getNutrition())) {
            existingProduct.setNutrition(productDTO.getNutrition());
        }
        if (!Objects.equals(existingProduct.getQuantity(), productDTO.getQuantity())) {
            existingProduct.setQuantity(productDTO.getQuantity());
        }
        if (!Objects.equals(existingProduct.getPackageQuantity(), productDTO.getPackageQuantity())) {
            existingProduct.setPackageQuantity(productDTO.getPackageQuantity());
        }
        if (!Objects.equals(existingProduct.getExpirationDate(), productDTO.getExpirationDate())) {
            existingProduct.setExpirationDate(productDTO.getExpirationDate());
        }
        if (!Objects.equals(existingProduct.getManufactureDate(), productDTO.getManufactureDate())) {
            existingProduct.setManufactureDate(productDTO.getManufactureDate());
        }
        if (!Objects.equals(existingProduct.getIsGmo(), productDTO.getIsGmo())) {
            existingProduct.setIsGmo(productDTO.getIsGmo());
        }
        if (!Objects.equals(existingProduct.getVolume(), productDTO.getVolume())) {
            existingProduct.setVolume(productDTO.getVolume());
        }
        if (!Objects.equals(existingProduct.getWishCount(), productDTO.getWishCount())) {
            existingProduct.setWishCount(productDTO.getWishCount());
        }

        // 기존 이미지 파일 삭제 및 새 이미지 저장
        if (productDTO.getProductImageFiles() != null && !productDTO.getProductImageFiles().isEmpty()) {
            // 기존 이미지 삭제
            List<String> existingImageNames = existingProduct.getProductImages().stream()
                    .map(ProductImage::getProductFileName)
                    .collect(Collectors.toList());
            imageUtil.deleteImage("product", existingImageNames);

            // 새 이미지 업로드
            List<String> newUploadFileNames = imageUtil.saveImages("product", productDTO.getProductImageFiles());

            // 새 이미지로 교체 (새 이미지가 있는 경우에만)
            if (newUploadFileNames != null && !newUploadFileNames.isEmpty()) {
                List<ProductImage> newProductImages = new ArrayList<>();
                newUploadFileNames.forEach(imageFileName -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setProductFileName(imageFileName);
                    productImage.setProduct(existingProduct);
                    newProductImages.add(productImage);
                });
                existingProduct.setProductImages(newProductImages);
            }
        } else {
            // 파일이 없으면 기존 이미지 삭제만 수행
            List<String> existingImageNames = existingProduct.getProductImages().stream()
                    .map(ProductImage::getProductFileName)
                    .collect(Collectors.toList());
            imageUtil.deleteImage("product", existingImageNames);
        }

        log.info("상품 수정 완료, pno: {}", pno);

        productRepository.save(existingProduct);

        return Map.of("RESULT", "SUCCESS");
    }
}

