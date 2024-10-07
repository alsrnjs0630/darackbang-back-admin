package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductReqDTO;
import com.lab.darackbang.entity.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductReqMapper {


    @Mapping(target = "productImages", ignore = true)
    ProductReqDTO toDTO(Product product);

    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "qandas", ignore = true)
    @Mapping(target = "productReviews", ignore = true)
    @Mapping(target = "subscribes", ignore = true)
    @Mapping(target = "wishLists", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Product toEntity(ProductReqDTO productReqDTO);

    @AfterMapping
    default void setDefaultValues(@MappingTarget Product product, ProductReqDTO productReqDTO) {
        if (productReqDTO.getIsVisible() == null) {
            product.setIsVisible(Boolean.TRUE);
        }
        if (productReqDTO.getIsSoldout() == null) {
            product.setIsSoldout(Boolean.FALSE);
        }
        if (productReqDTO.getIsGmo() == null) {
            product.setIsGmo(Boolean.FALSE);
        }
    }
}
