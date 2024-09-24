package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_exchange")
public class Exchange extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 교환아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 구독아이디
    @ManyToOne
    @JoinColumn(name = "subscribe_id", nullable = false)
    private Subscribe subscribe;

    // 반송배송업체
    @Column(name = "ret_company_name", nullable = false, length = 50)
    private String retCompanyName;

    // 반송배송송장번호
    @Column(name = "ret_shipping_no", nullable = false, length = 20)
    private String retShippingNo;

    // 반송주소
    @Column(name = "return_addr", nullable = false, length = 150)
    private String returnAddr;

    // 교환배송업체
    @Column(name = "ex_company_name", nullable = false, length = 50)
    private String exCompanyName;

    // 교환배송송장번호
    @Column(name = "ex_shipping_no", nullable = false, length = 20)
    private String exShippingNo;

    // 교환주소
    @Column(name = "ex_addr", nullable = false, length = 150)
    private String exAddr;

    // 교환우편번호
    @Column(name = "ex_post_no", nullable = false, length = 5)
    private String exPostNo;

    // 교환이유
    @Column(name = "ex_reason", nullable = false, length = 150)
    private String exReason;

    // 이미지파일명
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    // 반려사유
    @Column(name = "reject_reason", length = 150)
    private String rejectReason;

    // 교환상태 (default 01 : 교환대기, 02 : 집하, 03 : 교환상품 배송출발, 04 : 교환상품 배송중, 05 : 교환상품 배송완료, 06 : 반려)
    @ColumnDefault("'01'")
    @Column(name = "exchagne_state", nullable = false, length = 2)
    private String exchagneState;

}
