package com.lab.darackbang.dto.order;

import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.Order;
import com.lab.darackbang.entity.OrderItem;
import com.lab.darackbang.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * A DTO for the {@link com.lab.darackbang.entity.Order} entity.
 */
@Schema(description = "주문정보")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OrderItemDTO implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 인덱스번호
     */
    @NotNull
    @Schema(description = "인덱스번호", required = true)
    private Long id;

    @Schema(description = "상품가격", required = false)
    private Integer productPrice;

    @Schema(description = "상품수량", required = false)
    private Integer productQuantity;

    //총구매액
    @Schema(description = "상품정보", required = false)
    private ProductDTO product;

}
