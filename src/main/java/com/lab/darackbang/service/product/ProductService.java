package com.lab.darackbang.service.product;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductReqDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Map<String,String> create(ProductDTO productDTO, List<MultipartFile> files, List<MultipartFile> descFiles) throws IOException;

    PageDTO<ProductDTO,ProductSearchDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable);

    ProductDTO findOne(Long id);

    Map<String, String> delete(Long id);

    ResponseEntity<Resource> getFile(String fileName);

    Map<String,String> update(ProductReqDTO productReqDTO, List<MultipartFile> files, List<MultipartFile> descFiles) throws IOException;

    Map<String,String> active(Long id);

}
