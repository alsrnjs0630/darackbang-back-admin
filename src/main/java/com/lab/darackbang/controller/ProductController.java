package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductImageDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("admin/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Map<String, String> register(ProductDTO productDTO) {
        return productService.create(productDTO);
    }

    @GetMapping("/list")
    public PageDTO<ProductDTO> list(@ModelAttribute ProductSearchDTO productSearchDTO,
                                    @PageableDefault(size = 10, sort = "pno", direction = Sort.Direction.DESC) Pageable pageable) {

           return productService.findAll(productSearchDTO, pageable);
    }
}
