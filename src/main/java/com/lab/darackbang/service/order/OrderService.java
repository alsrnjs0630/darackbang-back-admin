package com.lab.darackbang.service.order;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.order.OrderDTO;
import com.lab.darackbang.dto.order.OrderSearchDTO;
import com.lab.darackbang.dto.product.ProductCartItemDTO;
import com.lab.darackbang.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    PageDTO<OrderDTO, OrderSearchDTO> findAll(OrderSearchDTO searchDTO, Pageable pageable);

    OrderDTO findOne(Long id);

    Order save(List<ProductCartItemDTO> productCartItemList);

}
