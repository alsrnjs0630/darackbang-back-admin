package com.lab.darackbang.dto.order;

import com.lab.darackbang.dto.member.MemberDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.entity.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link com.lab.darackbang.entity.Order} entity.
 */
@Schema(description = "주문정보")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OrderDTO implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 인덱스번호
     */
    @NotNull
    @Schema(description = "인덱스번호", required = true)
    private Long id;


    //총구매액
    @Schema(description = "총구매액", required = false)
    private Integer totalOrderPrice;

    //구매일
    @Schema(description = "구매일", required = false)
    private LocalDate orderDate;

    @Schema(description = "구매목록", required = false)
    private List<OrderItemDTO> orderItems;

    // 회원아이디
    @Schema(description = "사용자정보", required = false)
    private MemberDTO member;

    /**
     * 생성일자
     */
    @Schema(description = "생성일자", example = "2023-01-01T10:00:00")
    private LocalDateTime createdDate;

    /**
     * 수정일자
     */
    @Schema(description = "수정일자", example = "2023-01-02T10:00:00")
    private LocalDateTime updatedDate;
}
