package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.order.OrderItemDTO;
import com.lab.darackbang.entity.OrderItem;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDTO toDTO(OrderItem orderItem);

    OrderItem toEntity(OrderItemDTO orderItemDTO);

}
