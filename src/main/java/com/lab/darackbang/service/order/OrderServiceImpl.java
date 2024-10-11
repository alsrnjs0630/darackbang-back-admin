package com.lab.darackbang.service.order;

import com.lab.darackbang.criteria.OrderCriteria;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.order.OrderDTO;
import com.lab.darackbang.dto.order.OrderSearchDTO;
import com.lab.darackbang.dto.product.ProductCartItemDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Order;
import com.lab.darackbang.entity.OrderItem;
import com.lab.darackbang.exception.MemberNotFoundException;
import com.lab.darackbang.mapper.OrderMapper;
import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.repository.MemberRepository;
import com.lab.darackbang.repository.OrderRepository;
import com.lab.darackbang.repository.ProductRepository;
import com.lab.darackbang.security.dto.LoginDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final MemberRepository memberRepository;

    private final OrderMapper orderMapper;

    private final PageMapper pageMapper;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public OrderDTO findOne(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDTO).orElse(null);
    }

    @Override
    public Order save(List<ProductCartItemDTO> productCartItemList) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();
      
        log.info("이메일:{}",loginDTO.getUserEmail());
        
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof LoginDTO) {
                loginDTO = (LoginDTO) principal;
            }
        }


        String email = loginDTO.getUserEmail();

      //  Member members = memberRepository.findById(5L).orElseThrow(MemberNotFoundException::new);

        return memberRepository.findByUserEmail(email).map(member -> {

            Order order = Order.builder()
                    .member(member)
                    .orderDate(LocalDate.now())
                    .totalOrderPrice(productCartItemList.stream()
                            .mapToInt(productCartItemDTO -> productCartItemDTO.getProductPrice() * productCartItemDTO.getQuantity())
                            .sum())
                    .build();

            // Create a list of OrderItem using the productCartItemList
            List<OrderItem> orderItemList = productCartItemList.stream()
                    .map(productCartItemDTO -> productRepository.findById(productCartItemDTO.getId())
                            .map(product -> {
                                OrderItem orderItem = new OrderItem();
                                orderItem.setProductQuantity(productCartItemDTO.getQuantity());
                                orderItem.setProductPrice(product.getSalePrice());
                                orderItem.setProduct(product);
                                orderItem.setOrder(order);
                                return orderItem;
                            })
                            .orElse(null))
                    .filter(Objects::nonNull) // Filter out any null values in case of missing products
                    .collect(Collectors.toList());

            // Set the OrderItems in the Order
            order.setOrderItems(orderItemList);

            // Save the order and return it
            return orderRepository.save(order);

        }).orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
