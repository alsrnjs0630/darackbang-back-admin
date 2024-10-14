package com.lab.darackbang.repository;

import com.lab.darackbang.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class StatRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

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


    @Autowired
    private MemberRepository memberRepository;

    @Test
    void insert() {
        Member member = memberRepository.findById(5L).orElseThrow();


        Optional<Order> order =  orderRepository.findByMemberId(member.getId());

        if(order.isPresent()) {

            AgeMonthStat ageMonthStat = new AgeMonthStat();
            ageMonthStat.setAgeGroup(member.getAgeGroup());

            if(ageMonthStat.getSaleTotalPrice()!=null){
                ageMonthStat.setSaleTotalPrice(ageMonthStat.getSaleTotalPrice()+order.get().getTotalOrderPrice());
            }else{
                ageMonthStat.setSaleTotalPrice(order.get().getTotalOrderPrice());
            }

            ageMonthStat.setMonth(String.valueOf(order.get().getOrderDate().getMonthValue()));
            ageMonthStat.setYear(String.valueOf(order.get().getOrderDate().getYear()));
            ageMonthStatRepository.save(ageMonthStat);

            AgeQuarterStat ageQuarterStat = new AgeQuarterStat();
            ageQuarterStat.setAgeGroup(member.getAgeGroup());
            ageQuarterStat.setYear(String.valueOf(order.get().getCreatedDate().getYear()));
            ageQuarterStat.setQuarter(String.valueOf((order.get().getOrderDate().getMonthValue() - 1) / 3 + 1));

            if(ageQuarterStat.getSaleTotalPrice()!=null){
                ageQuarterStat.setSaleTotalPrice(ageQuarterStat.getSaleTotalPrice()+order.get().getTotalOrderPrice());
            }else{
                ageQuarterStat.setSaleTotalPrice(order.get().getTotalOrderPrice());
            }

            ageQuarterStatRepository.save(ageQuarterStat);

            AgeYearStat ageYearStat = new AgeYearStat();
            ageYearStat.setAgeGroup(member.getAgeGroup());
            ageYearStat.setYear(String.valueOf(order.get().getCreatedDate().getYear()));

            if(ageYearStat.getSaleTotalPrice()!=null){
                ageYearStat.setSaleTotalPrice(ageYearStat.getSaleTotalPrice()+order.get().getTotalOrderPrice());
            }else{
                ageYearStat.setSaleTotalPrice(order.get().getTotalOrderPrice());
            }


            ageYearStatRepository.save(ageYearStat);


            order.get().getOrderItems().forEach(orderItem -> {

                ProductMonthStat productMonthStat = new ProductMonthStat();
                productMonthStat.setPno(orderItem.getProduct().getPno());
                productMonthStat.setYear(String.valueOf(order.get().getCreatedDate().getYear()));
                productMonthStat.setMonth(String.valueOf(order.get().getCreatedDate().getMonthValue()));
                productMonthStat.setProductName(orderItem.getProduct().getProductName());
                if(productMonthStat.getSaleTotalPrice()!=null){
                    productMonthStat.setSaleTotalPrice(productMonthStat.getSaleTotalPrice()+(orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }else{
                    productMonthStat.setSaleTotalPrice((orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }

                productMonthStatRepository.save(productMonthStat);

                ProductQuarterStat productQuarterStat = new ProductQuarterStat();
                productQuarterStat.setPno(orderItem.getProduct().getPno());
                productQuarterStat.setYear(String.valueOf(order.get().getCreatedDate().getYear()));
                productQuarterStat.setQuarter(String.valueOf((order.get().getCreatedDate().getMonthValue() - 1) / 3 + 1));
                productQuarterStat.setProductName(orderItem.getProduct().getProductName());

                if(productQuarterStat.getSaleTotalPrice()!=null){
                    productQuarterStat.setSaleTotalPrice(productQuarterStat.getSaleTotalPrice()+(orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }else{
                    productQuarterStat.setSaleTotalPrice((orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }


                productQuarterStatRepository.save(productQuarterStat);

                ProductYearStat productYearStat = new ProductYearStat();
                productYearStat.setPno(orderItem.getProduct().getPno());
                productYearStat.setYear(String.valueOf(order.get().getCreatedDate().getYear()));

                if(productYearStat.getSaleTotalPrice()!=null){
                    productYearStat.setSaleTotalPrice(productYearStat.getSaleTotalPrice()+(orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }else{
                    productYearStat.setSaleTotalPrice((orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }

                productYearStat.setProductName(orderItem.getProduct().getProductName());
                productYearStatRepository.save(productYearStat);

            });



        }

    }


    @Test
    void insertproduct() {
        Member member = memberRepository.findById(5L).orElseThrow();


        Optional<Order> order =  orderRepository.findByMemberId(member.getId());

        if(order.isPresent()) {

       

            order.get().getOrderItems().forEach(orderItem -> {

                ProductMonthStat productMonthStat = new ProductMonthStat();
                productMonthStat.setYear(String.valueOf(order.get().getCreatedDate().getYear()));
                productMonthStat.setMonth(String.valueOf(order.get().getCreatedDate().getMonthValue()));
                productMonthStat.setProductName(orderItem.getProduct().getProductName());
                if(productMonthStat.getSaleTotalPrice()!=null){
                    productMonthStat.setSaleTotalPrice(productMonthStat.getSaleTotalPrice()+(orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }else{
                    productMonthStat.setSaleTotalPrice((orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }

                productMonthStatRepository.save(productMonthStat);

                ProductQuarterStat productQuarterStat = new ProductQuarterStat();
                productQuarterStat.setYear(String.valueOf(order.get().getCreatedDate().getYear()));
                productQuarterStat.setQuarter(String.valueOf((order.get().getCreatedDate().getMonthValue() - 1) / 3 + 1));
                productQuarterStat.setProductName(orderItem.getProduct().getProductName());

                if(productQuarterStat.getSaleTotalPrice()!=null){
                    productQuarterStat.setSaleTotalPrice(productQuarterStat.getSaleTotalPrice()+(orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }else{
                    productQuarterStat.setSaleTotalPrice((orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }


                productQuarterStatRepository.save(productQuarterStat);

                ProductYearStat productYearStat = new ProductYearStat();
                productYearStat.setYear(String.valueOf(order.get().getCreatedDate().getYear()));

                if(productYearStat.getSaleTotalPrice()!=null){
                    productYearStat.setSaleTotalPrice(productYearStat.getSaleTotalPrice()+(orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }else{
                    productYearStat.setSaleTotalPrice((orderItem.getProductPrice()*orderItem.getProductQuantity()));
                }

                productYearStat.setProductName(orderItem.getProduct().getProductName());
                productYearStatRepository.save(productYearStat);

            });



        }

    }

}
