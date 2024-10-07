package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductReqDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("admin/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public Map<String, String> register(ProductDTO productDTO, List<MultipartFile> files, List<MultipartFile> descFiles) throws IOException {
        return productService.create(productDTO, files, descFiles);
    }

    @GetMapping("/list")
    public PageDTO<ProductDTO,ProductSearchDTO> list(@ModelAttribute ProductSearchDTO productSearchDTO,
                                                     @PageableDefault(size = 10, sort = "pno", direction = Sort.Direction.DESC) Pageable pageable) {


        return productService.findAll(productSearchDTO, pageable);
    }

    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id) {
        return productService.findOne(id);
    }


    @DeleteMapping("/{id}")
    public  Map<String, String> delete(@PathVariable Long id) {
        return productService.delete(id);
    }


    /**
     * 상품이미지 보기
     * @param filename
     * @return
     * @throws IOException
     */
    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) throws IOException {
        return productService.getFile(filename);
    }

    /**
     * 상품정보 업데이트
     * @param productDTO
     * @param files
     * @param descFiles
     * @return
     * @throws IOException
     */
    @PutMapping("")
    public Map<String, String> update(ProductReqDTO productReqDTO, List<MultipartFile> files, List<MultipartFile> descFiles) throws IOException {
        return productService.update(productReqDTO, files, descFiles);
    }


    /**
     * 상품 삭제 취소
     * @param id
     * @return
     */
    @PutMapping("/active/{id}")
    public Map<String, String> active(@PathVariable Long id){
        return productService.active(id);
    }



}

