package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.order.OrderDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductImageDTO;
import com.lab.darackbang.entity.Order;
import com.lab.darackbang.entity.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Comparator;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toDTO(Order order);

    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "member.memberRoles", ignore = true)
    @Mapping(target = "member.memberCard", ignore = true)
    @Mapping(target = "member.subscribes", ignore = true)
    @Mapping(target = "member.cart", ignore = true)
    @Mapping(target = "member.qandas", ignore = true)
    @Mapping(target = "member.wishlists", ignore = true)
    @Mapping(target = "member.productReviews", ignore = true)
    @Mapping(target = "member.orders", ignore = true)
    Order toEntity(OrderDTO orderDTO);  // Corrected to map to Order instead of Product
}