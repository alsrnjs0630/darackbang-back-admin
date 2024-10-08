package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductImageDTO;
import com.lab.darackbang.entity.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Comparator;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = ProductImageMapper.class)
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    @Mapping(source = "productImages", target = "productImages")
    @Mapping(target = "qandas", ignore = true)
    @Mapping(target = "productReviews", ignore = true)
    @Mapping(target = "subscribes", ignore = true)
    @Mapping(target = "wishLists", ignore = true)
    Product toEntity(ProductDTO productDTO);


    @AfterMapping
    default void setDefaultValues(@MappingTarget Product product, ProductDTO productDTO) {
        if (productDTO.getIsVisible() == null) {
            product.setIsVisible(Boolean.TRUE);
        }
        if (productDTO.getIsSoldout() == null) {
            product.setIsSoldout(Boolean.FALSE);
        }
        if (productDTO.getIsGmo() == null) {
            product.setIsGmo(Boolean.FALSE);
        }
        if (productDTO.getIsDeleted() == null) {
            product.setIsDeleted(Boolean.FALSE);
        }
        if (productDTO.getWishCount() == null) {
            product.setWishCount(0);
        }
    }


    @AfterMapping
    default void setDefaultValues(@MappingTarget ProductDTO productDTO) {

        productDTO.setCategory(productDTO.getCategory().equals("L01")? "잎차":productDTO.getCategory().equals("B01")? "티백":"열매");

        productDTO.setProductImages(
                productDTO.getProductImages().stream()
                        .filter(productImage -> Boolean.FALSE.equals(productImage.getIsDeleted())) // isDeleted가 false인 항목만 필터링
                        .sorted(Comparator.comparing(ProductImageDTO::getSortNum)) // sortNum 기준으로 오름차순 정렬
                        .collect(Collectors.toList()) // 필터링 및 정렬된 리스트 수집
        );
    }
}
