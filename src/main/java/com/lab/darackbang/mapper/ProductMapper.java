package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductImageMapper.class)
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    @Mapping(source = "productImages", target = "productImages")
    Product toEntity(ProductDTO productDTO);
}
