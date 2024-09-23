package com.lab.darackbang.repository;

import com.lab.darackbang.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private SubscribeRepository subscribeRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private AgeMonthStatRepository ageMonthStatRepository;
    @Autowired
    private AgeQuarterStatRepository ageQuarterStatRepository;
    @Autowired
    private AgeYearStatRepository ageYearStatRepository;
    @Autowired
    private ProductMonthStatRepository productMonthStatRepository;
    @Autowired
    private ProductQuarterStatRepository productQuarterStatRepository;
    @Autowired
    private ProductYearStatRepository productYearStatRepository;

    @Test
    public void cartItemUpdateTest() {

        Cart cart = cartRepository.findByMemberId(5L).orElseThrow();

        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cart.getId());

        cart.getCartItems().forEach(cartItem -> {
            log.info("cartItem: {}", cartItem.getQuantity());
        });

        Payment payment = Payment.builder()
                .paymentId("123451123412")
                .paymentPrice(cart.getCartItems().stream().mapToInt(carts -> carts.getProductPrice() * carts.getQuantity()).sum())
                .paymentState("02")
                .paymentDate(LocalDate.now())
                .build();

        paymentRepository.save(payment);

        List<Subscribe> subscribeList = new ArrayList<>();

        cart.getCartItems().forEach(cartItem -> {
            Subscribe subscribe = Subscribe.builder()
                    .member(cart.getMember())
                    .product(cartItem.getProduct())
                    .subQuantity(cartItem.getQuantity())
                    .subPrice(cartItem.getProductPrice() * cartItem.getQuantity())
                    .subscribeDate(LocalDate.now())
                    .paymentDate(LocalDate.now())
                    .shippingDate(LocalDate.now())
                    .subState("01")
                    .build();

            subscribeList.add(subscribe);
        });

        List<Subscribe> createSubscribeList = subscribeRepository.saveAll(subscribeList);
        insertOrderHistory(cart);

        insertDelivery(createSubscribeList);
    }

    private void insertOrderHistory(Cart cart) {

        OrderHistory orderHistory = OrderHistory.builder()
                .member(cart.getMember())
                .orderDate(LocalDate.now())
                .build();

        List<OrderItem> orderItems = new ArrayList<>();

        cart.getCartItems().forEach(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .product(cartItem.getProduct())
                    .orderHistory(orderHistory)
                    .productPrice(cartItem.getProductPrice())
                    .orderQuantity(cartItem.getQuantity())
                    .build();

            orderItems.add(orderItem);
        });

        orderHistory.setOrderItems(orderItems);

        orderHistoryRepository.save(orderHistory);

    }

    private void insertDelivery(List<Subscribe> subscribeList) {

        subscribeList.forEach(subscribe -> {

            Delivery delivery = Delivery.builder()
                    .subscribe(subscribe)
                    .companyName("CJ대한통운")
                    .deliveryNo("12345678912345")
                    .deliveryAddr("서울 금천구 가산디지털2로 101")
                    .dlyPostNo("08505")
                    .deliveryState("01")
                    .build();

            deliveryRepository.save(delivery);
        });

    }



    private void insertStatics(List<CartItem> cartItemList) {

        //현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        //연도
        String year = String.valueOf(currentDate.getYear());

        //월
        String month = String.valueOf(currentDate.getMonthValue());

        //분기
        String quarter = String.valueOf((Integer.parseInt(month) - 1) / 3 + 1);


        CartItem cartItem = cartItemList.stream().findFirst().get();

        if (cartItem != null) {


            cartItemList.forEach(cartItem1 -> {

                //연령별 년, 분기, 월 통계 데이터 생성 혹은 업데잍,
                Optional<AgeMonthStat> findAgeMonthStat = ageMonthStatRepository.findByMonthAndYearAndAgeGroup(month, year, cartItem1.getCart().getMember().getAgeGroup());

                //연령별 월 업데이트
                if (findAgeMonthStat != null) {

                    Integer totalPrice = cartItemList.stream().mapToInt(carts -> carts.getProductPrice() * carts.getQuantity()).sum();
                    findAgeMonthStat.get().setSaleTotalPrice(findAgeMonthStat.get().getSaleTotalPrice() + (cartItem1.getProductPrice()*cartItem1.getQuantity()));
                    ageMonthStatRepository.save(findAgeMonthStat.get());

                    //연령별 월 생성
                } else {
                    AgeMonthStat ageMonthStat = AgeMonthStat.builder().month(month).year(year).ageGroup(cartItem.getCart().getMember().getAgeGroup()).build();

                    ageMonthStatRepository.save(ageMonthStat);
                }

                //연령별 년

                //연령별 분기


                //월, 분기, 년 상품 통계 데이터 생성 혹은 업데이트

               // cartItem1.getProduct().getProductName()

            });

        }


    }
}
