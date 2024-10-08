package com.lab.darackbang.service.order;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.order.OrderDTO;
import com.lab.darackbang.dto.order.OrderSearchDTO;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    PageDTO<OrderDTO, OrderSearchDTO> findAll(OrderSearchDTO searchDTO, Pageable pageable);

    OrderDTO findOne(Long id);

}
