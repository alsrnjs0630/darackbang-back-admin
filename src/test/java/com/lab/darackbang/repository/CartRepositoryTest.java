package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Cart;
import com.lab.darackbang.entity.CartItem;
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
        Member member = memberRepository.findById(5L).orElseThrow();
        Cart cart = Cart.builder().member(member)
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        List<CartItem> cartItemList = new ArrayList<>();

        for(int i=3; i<=7; i++) {
            Product product = productRepository.findById(Long.parseLong(String.valueOf(i))).orElseThrow();
            CartItem cartItem = CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(3)
                            .productPrice(product.getSalePrice())
                            .build();

            cartItemList.add(cartItem);
        }

        cart.setCartItems(cartItemList);

        cartRepository.save(cart);
    }

}
