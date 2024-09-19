package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Product;
import com.lab.darackbang.entity.ProductImage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Test
    void productInsertTest() {

        for(int i=1; i<=10; i++) {

            String pno =null;

            Optional<Product> findProduct = productRepository.findTopByOrderByPnoDesc();

            if(findProduct.isPresent()){
                pno = genPno(findProduct.get().getPno());
            }else{
                pno= "TP0000000001";
            }

            Product product = Product.builder()
                    .pno(pno)
                    .productName("녹차")
                    .retailPrice(15000)
                    .salePrice(15000)
                    .isVisible(true)
                    .manufacture("오설록")
                    .origin("제주도")
                    .material("녹차")
                    .category("잎차")
                    .saleCompany("오설록")
                    .isDeleted(false)
                    .isSoldout(false)
                    .quantity(24)
                    .expirationDate(LocalDate.of(2025,9,19))
                    .manufactureDate(LocalDate.of(2024,9,1))
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .isGmo(false)
                    .wishCount(0)
                    .build();

            List<ProductImage> productImages = new ArrayList<>();

            ProductImage productImage = new ProductImage();
            productImage.setProductFileName("test");
            productImage.setProduct(product);
            productImages.add(productImage);

            product.setProductImages(productImages);

            productRepository.save(product);
        }
    }

    @Test
    @Transactional
    void productReadTest() {
        Product product = productRepository.findById(25L).orElseThrow();
        log.info("상품 이름 {}", product.getProductName());
        log.info("이미지 파일명 {}", product.getProductImages().stream().findFirst().get().getProductFileName());
    }

    @Test
    void productUpdateTest() {
        Product product = productRepository.findById(25L).orElseThrow();
        product.setProductName("홍차");

        productRepository.save(product);

        List<ProductImage> productImageList = productImageRepository.findByProductId(product.getId());

        productImageList.stream().forEach(productImage -> {
            productImage.setProductFileName("새 이미지");
        });

        productImageRepository.saveAll(productImageList);
    }

    @Test
    void productDeleteTest() {
        Product product = productRepository.findById(25L).orElseThrow();
        product.setIsDeleted(true);

        productRepository.save(product);

    }

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
        }else{
            return "TP0000000001";
        }

        return "TP0000000001";
    }
}
