package com.lab.darackbang.repository;

import com.lab.darackbang.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

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
/*
    @Test
   void cartItemUpdateTest() {

        Cart cart = cartRepository.findByMemberId(5L).orElseThrow();

        cart.getCartItems().forEach(cartItem -> {
            log.info("cartItem: {}", cartItem.getQuantity());
        });

        List<CartItem> newCartItems = new ArrayList<>();
        BeanUtils.copyProperties(cart.getCartItems(), newCartItems);

        ////////////////////////////////// 결제 내역 생성 시작
        Payment payment = Payment.builder()
                .member(cart.getMember())
                .paymentId("123451123412")
                .paymentPrice(cart.getCartItems().stream().mapToInt(carts -> carts.getProductPrice() * carts.getQuantity()).sum())
                .paymentState("02")
                .paymentDate(LocalDate.now())
                .build();

        paymentRepository.save(payment);
        ////////////////////////////////// 결제 내역 생성 끝

        ////////////////////////////////// 구독 내역 생성 시작
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

        ////////////////////////////////// 구독 내역 생성 끝

        /////// 주문내역 생성 시작

        Order orderHistory = Order.builder()
                .member(cart.getMember())
                .orderDate(LocalDate.now())
                .build();

        orderHistory.setOrderItems(new ArrayList<>());


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

        /////////////////////////////////////////////// 주문 내역 생성 끝

        //////////////////////////////////////////////  배송 내역 생성 시작
        createSubscribeList.forEach(subscribe -> {

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


        //////////////////////////////////////////////  배송 내역 생성 끝
    }
*/

    @Test
    //@Transactional
    public void insertStatics() {

        Cart cart = cartRepository.findByMemberId(5L).orElseThrow();

        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cart.getId());

        cartItemList.forEach(cartItem -> {
            log.info("getProductName: {}", cartItem.getProduct().getProductName());
        });

        //현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        //연도
        String year = String.valueOf(currentDate.getYear());

        //월
        String month = String.valueOf(currentDate.getMonthValue());

        //분기
        String quarter = String.valueOf((int)Math.ceil(currentDate.getMonthValue() / 3.0));

        cartItemList.stream().findFirst().ifPresent(cartItem -> cartItemList.forEach(cartItem1 -> {

            //연령대
            String ageGroup = cart.getMember().getAgeGroup();

            log.info("ageGroup: {}", ageGroup);

            //상품명
            String productName = cartItem1.getProduct().getProductName();

            log.info("productName: {}", productName);
            //cartItem 테이블의 특정한 상품의 총 금액
            Integer totalPrice = cartItem1.getProductPrice() * cartItem1.getQuantity();

            //ifPresentOrElse 를 사용 하여 데이터가 있으면 업데이트 없으면 생성 하는 코드로 변경함.

            // 연령별 월 통계 데이터 조회
            ageMonthStatRepository.findByMonthAndYearAndAgeGroup(month, year, ageGroup)
                    .ifPresentOrElse(
                            // 통계 데이터가 존재하면 업데이트
                            ageMonthStat -> {
                                int updatedSaleTotalPrice = ageMonthStat.getSaleTotalPrice() + totalPrice;
                                ageMonthStat.setSaleTotalPrice(updatedSaleTotalPrice);
                                ageMonthStatRepository.save(ageMonthStat);
                            },
                            // 통계 데이터가 존재하지 않으면 새로 생성
                            () -> {

                                AgeMonthStat newAgeMonthStat = AgeMonthStat.builder()
                                        .month(month)
                                        .year(year)
                                        .ageGroup(ageGroup)
                                        .saleTotalPrice(totalPrice)
                                        .build();
                                ageMonthStatRepository.save(newAgeMonthStat);
                            }
                    );

            // 연령별 년 통계 데이터 조회
            ageYearStatRepository.findByYearAndAgeGroup(year, ageGroup)
                    .ifPresentOrElse(
                            ageYearStat -> {
                                int updatedSaleTotalPrice = ageYearStat.getSaleTotalPrice() + totalPrice;
                                ageYearStat.setSaleTotalPrice(updatedSaleTotalPrice);
                                ageYearStatRepository.save(ageYearStat);
                            },
                            () -> {
                                AgeYearStat newAgeYearStat = AgeYearStat.builder()
                                        .year(year)
                                        .ageGroup(ageGroup)
                                        .saleTotalPrice(totalPrice)
                                        .build();
                                ageYearStatRepository.save(newAgeYearStat);
                            }
                    );

            // 연령별 분기 통계 데이터 조회 추가
            ageQuarterStatRepository.findByQuarterAndYearAndAgeGroup(quarter, year, ageGroup)
                    .ifPresentOrElse(
                            ageQuarterStat -> {
                                int updatedSaleTotalPrice = ageQuarterStat.getSaleTotalPrice() + totalPrice;
                                ageQuarterStat.setSaleTotalPrice(updatedSaleTotalPrice);
                                ageQuarterStatRepository.save(ageQuarterStat);
                            },
                            () -> {
                                AgeQuarterStat newAgeQuarterStat = AgeQuarterStat.builder()
                                        .year(year)
                                        .quarter(quarter)
                                        .ageGroup(ageGroup)
                                        .saleTotalPrice(totalPrice)
                                        .build();
                                ageQuarterStatRepository.save(newAgeQuarterStat);
                            }
                    );

            // 상품별 년 통계 데이터 조회 추가
            productYearStatRepository.findByYearAndProductName(year, productName)
                    .ifPresentOrElse(
                            productYearStat -> {
                                int updatedSaleTotalPrice = productYearStat.getSaleTotalPrice() + totalPrice;
                                productYearStat.setSaleTotalPrice(updatedSaleTotalPrice);
                                productYearStatRepository.save(productYearStat);
                            },
                            () -> {
                                ProductYearStat newProductYearStat = ProductYearStat.builder()
                                        .year(year)
                                        .productName(productName)
                                        .saleTotalPrice(totalPrice)
                                        .build();
                                productYearStatRepository.save(newProductYearStat);
                            }
                    );

            // 상품별 분기 통계 데이터 조회 추가
            productQuarterStatRepository.findByQuarterAndYearAndProductName(year, quarter, productName)
                    .ifPresentOrElse(
                            productQuarterStat -> {
                                int updatedSaleTotalPrice = productQuarterStat.getSaleTotalPrice() + totalPrice;
                                productQuarterStat.setSaleTotalPrice(updatedSaleTotalPrice);
                                productQuarterStatRepository.save(productQuarterStat);
                            },
                            () -> {
                                ProductQuarterStat newProductQuarterStat = ProductQuarterStat.builder()
                                        .year(year)
                                        .quarter(quarter)
                                        .productName(productName)
                                        .saleTotalPrice(totalPrice)
                                        .build();
                                productQuarterStatRepository.save(newProductQuarterStat);
                            }
                    );

            // 상품별 월 통계 데이터 조회 추가
            productMonthStatRepository.findByMonthAndYearAndProductName(year, month, productName)
                    .ifPresentOrElse(
                            productMonthStat -> {
                                int updatedSaleTotalPrice = productMonthStat.getSaleTotalPrice() + totalPrice;
                                productMonthStat.setSaleTotalPrice(updatedSaleTotalPrice);
                                productMonthStatRepository.save(productMonthStat);
                            },
                            () -> {
                                ProductMonthStat newProductMonthStat = ProductMonthStat.builder()
                                        .year(year)
                                        .month(month)
                                        .productName(productName)
                                        .saleTotalPrice(totalPrice)
                                        .build();
                                productMonthStatRepository.save(newProductMonthStat);
                            }
                    );
        }));

    }

    @Test
    private void cartItemDeleteTest() {

        Cart cart = cartRepository.findByMemberId(5L).orElseThrow();
        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cart.getId());

    }
}
