package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.dto.payment.PaymentDTO;
import com.lab.darackbang.entity.Payment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDTO(Payment payment);

    @Mapping(source = "order.orderItems", target = "order.orderItems")
    @Mapping(source = "order.member",target = "order.member")
    Payment toEntity(PaymentDTO paymentDTO);  // Corrected to map to Order instead of Product


    @AfterMapping
    default void setDefaultValues(@MappingTarget PaymentDTO paymentDTO, Payment payment) {
        if (payment.getSuccess()) {
            paymentDTO.setSuccess("성공");
        } else {
            paymentDTO.setSuccess("실패");
        }
    }
    
}