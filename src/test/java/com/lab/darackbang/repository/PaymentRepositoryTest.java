package com.lab.darackbang.repository;

import com.lab.darackbang.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@SpringBootTest
@Slf4j
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    //@Transactional
    public void insertPayment() {

       // List<Product> products = productRepository.findAllById(List.of(1L, 2L, 3L));
     //   products = productRepository.findAllById(List.of(1L, 2L, 3L));

     //   Member member = memberRepository.findById(5L).orElseThrow();

        Order order = orderRepository.findByMemberId(5L).orElseThrow();

        //이 상품 정보와 사용자 정보를 가지고  Order 정보를 만든다.

        long timestamp = Long.parseLong("1727255778");

        Payment payment = new Payment();
        payment.setBuyerAddr("서울특별시 강남구 삼성동");
        payment.setBuyerEmail("user10@test.com");
        payment.setName("user10");
        payment.setBuyerPostcode("123-456");
        payment.setBuyerTel("010-2881-0137");
        payment.setCardQuota("0");
        payment.setCurrency("KRW");
        payment.setCustomerUid("10_202410100920");
        payment.setImpUid("imp_450747622765");
        payment.setMerchantUid("PNO_10_202410100920");
        payment.setName("히비스커스 선샤인 외 2건");
        payment.setPaidAmount(120000);
        payment.setPaidAt( LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()));
        payment.setPayMethod("point");
        payment.setPgProvider("kakaopay");
        payment.setPgTid("T6f3d4c37f575eb00738");
        payment.setPgType("payment");
        payment.setReceiptUrl("https://mockup-pg-web.kakao.com/v1/confirmation/p/T6f3d4c37f575eb00738/b2e3b7ff8732de253a03735b9b294e07a7d6e2f00b97fa262b3f3961c4fedb56");
        payment.setStatus("paid");
        payment.setSuccess(true);
        payment.setOrder(order);
        paymentRepository.save(payment);
    }

}
