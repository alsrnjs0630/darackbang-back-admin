package com.lab.darackbang.dto.product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCartItemDTO {
    private Long id;
    private Integer quantity;
    private Integer productPrice;
}
