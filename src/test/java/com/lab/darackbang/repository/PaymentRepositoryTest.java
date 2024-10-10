package com.lab.darackbang.repository;

import com.lab.darackbang.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;


    @Autowired
    OrderRepository orderRepository;

    @Test
    //@Transactional
    public void insertPayment() {

        Order order = orderRepository.findById(1L).orElseThrow();

    }

}
