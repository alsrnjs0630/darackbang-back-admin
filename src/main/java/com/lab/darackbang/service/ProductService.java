package com.lab.darackbang.service;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    PageDTO<ProductDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable);
}
