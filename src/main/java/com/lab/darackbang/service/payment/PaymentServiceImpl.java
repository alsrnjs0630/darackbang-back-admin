package com.lab.darackbang.service.payment;


import com.lab.darackbang.criteria.PaymentCriteria;
import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.payment.PaymentDTO;
import com.lab.darackbang.dto.payment.PaymentResDTO;
import com.lab.darackbang.dto.payment.PaymentSearchDTO;
import com.lab.darackbang.entity.Order;
import com.lab.darackbang.entity.Payment;
import com.lab.darackbang.exception.NotFoundException;

import com.lab.darackbang.mapper.PageMapper;
import com.lab.darackbang.mapper.PaymentMapper;
import com.lab.darackbang.mapper.PaymentResMapper;
import com.lab.darackbang.repository.OrderRepository;
import com.lab.darackbang.repository.PaymentRepository;
import com.lab.darackbang.repository.ProductRepository;
import com.lab.darackbang.service.order.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final PaymentMapper paymentMapper;

    private final PaymentResMapper paymentResMapper;

    private final PageMapper pageMapper;

    private  final OrderService orderService;

    @Override
    @Transactional(readOnly = true)
    public PageDTO<PaymentDTO, PaymentSearchDTO> findAll(PaymentSearchDTO searchDTO, Pageable pageable) {
        log.info(searchDTO.toString());

        Specification<Payment> spec = PaymentCriteria.byCriteria(searchDTO);

        //페이지 번호(프론트에서는 1부터 시작하지만 실제로는 현재 페이지번호 -1)
        int pageNumber = (pageable.getPageNumber() < 1) ? 0 : pageable.getPageNumber() - 1;

        Pageable correctedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());

        // JPA 리포지토리를 사용하여 페이징을 적용한 주문 목록 조회 후, OrderMapper를 통해 OrderDTO로 변환
        return pageMapper.toDTO(paymentRepository.findAll(spec, correctedPageable).map(paymentMapper::toDTO), searchDTO);
    }

    @Override
    public Map<String, String> save(PaymentResDTO paymentDTO) {

        Payment payment = paymentResMapper.toEntity(paymentDTO);

        payment.setOrder(orderService.save(paymentDTO.getProductCartItemList()));
        //Order order = orderRepository.findByMemberId(5L).orElseThrow(NotFoundException::new);
        //payment.setOrder(order);

        paymentRepository.save(payment);

        return Map.of("RESULT", "SUCCESS");
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDTO findOne(Long id) {
        return paymentRepository.findById(id).map(paymentMapper::toDTO).orElse(null);
    }
}
