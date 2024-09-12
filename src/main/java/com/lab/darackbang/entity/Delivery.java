package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "tbl_delivery")
public class Delivery {

    // 배송아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 구독아이디
    @ManyToOne
    @JoinColumn(name = "subscribe_id", nullable = false)
    private Subscribe subscribe;

    // 배송업체
    @Column(name = "company_name", nullable = false, length = 50)
    private String companyName;

    // 배송송장번호
    @Column(name = "delivery_no", nullable = false, length = 20)
    private String deliveryNo;

    // 배송주소
    @Column(name = "delivery_addr", nullable = false, length = 150)
    private String deliveryAddr;

    // 배송우편번호
    @Column(name = "dly_post_no", nullable = false, length = 5)
    private String dlyPostNo;

    // 배송상태 (default 01 : 배송대기, 02 : 집하, 03 : 배송출발, 04 : 배송중, 05 : 배송완료)
    @Builder.Default
    @Column(name = "delivery_state", nullable = false, length = 2)
    private String deliveryState = "01";

    // 등록일
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    // 수정일
    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    private LocalDate updatedDate;
}
