package com.lab.darackbang.service.payment;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.order.OrderDTO;
import com.lab.darackbang.dto.order.OrderSearchDTO;
import com.lab.darackbang.dto.payment.PaymentDTO;
import com.lab.darackbang.dto.payment.PaymentResDTO;
import com.lab.darackbang.dto.payment.PaymentSearchDTO;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PaymentService {

    PageDTO<PaymentDTO, PaymentSearchDTO> findAll(PaymentSearchDTO searchDTO, Pageable pageable);

    Map<String, String> save(PaymentResDTO paymentDTO);

    PaymentDTO findOne(Long id);

}
