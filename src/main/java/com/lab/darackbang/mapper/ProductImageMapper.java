package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.product.ProductImageDTO;
import com.lab.darackbang.entity.ProductImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImageDTO toDTO(ProductImage productImage);

    ProductImage toEntity(ProductImageDTO productImageDTO);
}
