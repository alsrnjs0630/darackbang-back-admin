package com.lab.darackbang.repository;

import com.lab.darackbang.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void orderInsertTest() {

        Member member = memberRepository.findById(5L).orElseThrow();

        Optional<Cart> cart = cartRepository.findByMemberId(member.getId());

        if (cart.isPresent()) {

            Order order = Order.builder()
                    .member(member)
                    .orderDate(LocalDate.now()).totalOrderPrice(cart.get().getCartItems().stream().mapToInt(CartItem::getProductPrice).sum())
                    .build();

            List<OrderItem> orderItems = new ArrayList<>();

            cart.get().getCartItems().forEach(cartItem -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductQuantity(cartItem.getQuantity());
                orderItem.setProductPrice(cartItem.getProductPrice());
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setOrder(order);

                orderItems.add(orderItem);

            });

            order.setOrderItems(orderItems);
            orderRepository.save(order);

        }


    }

}