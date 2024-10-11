package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.dto.payment.PaymentDTO;
import com.lab.darackbang.entity.Payment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDTO(Payment payment);



    @AfterMapping
    default void setDefaultValues(@MappingTarget PaymentDTO paymentDTO, Payment payment) {
        if (payment.getSuccess()) {
            paymentDTO.setSuccess("성공");
        } else {
            paymentDTO.setSuccess("실패");
        }
    }
    
}