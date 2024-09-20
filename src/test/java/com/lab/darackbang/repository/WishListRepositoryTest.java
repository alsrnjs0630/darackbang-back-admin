package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Product;
import com.lab.darackbang.entity.WishList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
public class WishListRepositoryTest {
    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertWish() {
        Member member = memberRepository.findById(5L).orElseThrow();
        Product product = productRepository.findById(8L).orElseThrow();

        WishList wishList = WishList.builder()
                .member(member)
                .product(product)
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        wishListRepository.save(wishList);

        product.setWishCount(product.getWishCount() + 1);

        productRepository.save(product);
    }

    @Test
    public void deleteWish() { // 관심상품 삭제 ( 삭제유무 1로 변경 )
        WishList wishList = wishListRepository.findById(2L).orElseThrow();
        Product product = productRepository.findById(wishList.getProduct().getId()).orElseThrow();

        wishListRepository.delete(wishList);
        product.setWishCount(product.getWishCount() - 1);

        productRepository.save(product);
        wishListRepository.save(wishList);
    }


}
