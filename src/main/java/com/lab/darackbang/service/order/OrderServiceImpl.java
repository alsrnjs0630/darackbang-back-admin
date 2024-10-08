package com.lab.darackbang.service.order;

import com.lab.darackbang.criteria.OrderCriteria;
import com.lab.darackbang.criteria.ProductCriteria;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.order.OrderDTO;
import com.lab.darackbang.dto.order.OrderSearchDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.entity.Order;
import com.lab.darackbang.entity.Product;
import com.lab.darackbang.mapper.OrderMapper;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDTO<OrderDTO, OrderSearchDTO> findAll(OrderSearchDTO searchDTO, Pageable pageable) {
        log.info(searchDTO.toString());

        Specification<Order> spec = OrderCriteria.byCriteria(searchDTO);

        //페이지 번호(프론트에서는 1부터 시작하지만 실제로는 현재 페이지번호 -1)
        int pageNumber = (pageable.getPageNumber() < 1) ? 0 : pageable.getPageNumber() - 1;

        Pageable correctedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());

        // JPA 리포지토리를 사용하여 페이징을 적용한 주문 목록 조회 후, OrderMapper를 통해 OrderDTO로 변환
        return pageMapper.toDTO(orderRepository.findAll(spec, correctedPageable).map(orderMapper::toDTO), searchDTO);
    }

    @Override
    public OrderDTO findOne(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDTO).orElse(null);
    }
}
