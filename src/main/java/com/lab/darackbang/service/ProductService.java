package com.lab.darackbang.service;

import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable);
}
