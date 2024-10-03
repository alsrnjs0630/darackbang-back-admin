package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("admin")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public Map<String, String> register(ProductDTO productDTO) {
        log.info("상품 등록 요청 성공");
        return productService.create(productDTO);
    }

    @GetMapping("/products/{imageName}")
    public ResponseEntity<Resource> viewImage(@PathVariable String imageName) {
        return productService.getProductImage(imageName);
    }

    @GetMapping("/products/list")
    public PageDTO<ProductDTO> list(@ModelAttribute ProductSearchDTO productSearchDTO,
                                    @PageableDefault(size = 10, sort = "pno", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("상품리스트 출력 요청 성공");
        return productService.findAll(productSearchDTO, pageable);
    }

    @PutMapping("/products/{pno}")
    public Map<String, String> modify(@PathVariable String pno, ProductDTO productDTO) {
        log.info("상품 수정 요청 성공");
        return productService.update(pno, productDTO);
    }

    @DeleteMapping("/products/{pno}")
    public Map<String, String> delete(@PathVariable String pno) {
        log.info("상품 삭제 요청 성공");
        return productService.delete(pno);
    }
}
