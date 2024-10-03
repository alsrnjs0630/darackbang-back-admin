package com.lab.darackbang.service;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProductService {

    Map<String,String> create(ProductDTO productDTO);

    ResponseEntity<Resource> getProductImage(String imageName);

    PageDTO<ProductDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable);

    Map<String, String> update(String pno, ProductDTO productDTO);

    Map<String, String> delete(String pno);
}