package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductImageMapper.class)
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    @Mapping(source = "productImages", target = "productImages")
    @Mapping(target = "qandas", ignore = true)
    @Mapping(target = "productReviews", ignore = true)
    @Mapping(target = "subscribes", ignore = true)
    @Mapping(target = "wishLists", ignore = true)
    Product toEntity(ProductDTO productDTO);
}
