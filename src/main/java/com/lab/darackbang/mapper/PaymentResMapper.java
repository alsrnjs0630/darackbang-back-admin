package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.payment.PaymentResDTO;
import com.lab.darackbang.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentResMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    Payment toEntity(PaymentResDTO paymentResDTO);  // Corrected to map to Order instead of Product
}