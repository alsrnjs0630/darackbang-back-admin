package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Cart;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void cartInsertTest() {
        Member member = memberRepository.findById(15L).orElseThrow();
        List<Cart> cartList = new ArrayList<>();

        for(int i=22; i<=26; i++) {
            Product product = productRepository.findById(Long.parseLong(String.valueOf(i))).orElseThrow();
            Cart cart = Cart.builder().member(member).product(product)
                    .productQuantity(2)
                    .productTotalPrice(product.getSalePrice()*2)
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .build();
            cartList.add(cart);
        }

        cartRepository.saveAll(cartList);
    }

}
