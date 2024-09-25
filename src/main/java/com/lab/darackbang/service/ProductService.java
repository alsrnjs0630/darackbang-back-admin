package com.lab.darackbang.service;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductService {

    Map<String,String> create(ProductDTO productDTO);

    PageDTO<ProductDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable);
}
