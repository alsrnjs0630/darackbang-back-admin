package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.order.OrderDTO;
import com.lab.darackbang.dto.order.OrderSearchDTO;
import com.lab.darackbang.dto.payment.PaymentDTO;
import com.lab.darackbang.dto.payment.PaymentResDTO;
import com.lab.darackbang.dto.payment.PaymentSearchDTO;
import com.lab.darackbang.service.order.OrderService;
import com.lab.darackbang.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("admin/payments")
public class PaymentController {

    private final OrderService orderService;

private final PaymentService paymentService;


    @GetMapping("/list")
    public PageDTO<PaymentDTO, PaymentSearchDTO> list(@ModelAttribute PaymentSearchDTO paymentSearchDTO,
                                                      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("사용자 리스트");

        return paymentService.findAll(paymentSearchDTO, pageable);
    }

    @GetMapping("/{id}")
    public PaymentDTO get(@PathVariable Long id) {
        return paymentService.findOne(id);
    }


    @PostMapping("/create")
    public Map<String,String> create(@RequestBody PaymentResDTO paymentResDTO) {
        return paymentService.save(paymentResDTO);
    }
}
