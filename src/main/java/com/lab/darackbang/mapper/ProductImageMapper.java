package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.product.ProductImageDTO;
import com.lab.darackbang.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImageDTO toDTO(ProductImage productImage);

    @Mapping(target = "product" , ignore = true)
    ProductImage toEntity(ProductImageDTO productImageDTO);
}
