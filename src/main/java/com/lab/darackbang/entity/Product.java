package com.lab.darackbang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode      //equals, hashcode 메서드 생성(데이터 타입에 맞는지 검사)
@Table(name = "tbl_product")
public class Product {
    //상품아이디
    //@Id: 기본키
    //@GeneratedValue: 시퀀스 생성
    //@Column(name: 칼럼명, nullable: Null 허용 여부, length: 데이터 타입의 길이)
    //: 엔티티 클래스의 필드를 데이터베이스 테이블의 열(column)과 매핑
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //상품번호
    @Column(name = "pno", nullable = false, length = 12)
    private String pno;

    //상품명
    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    //상품설명
    @Column(name = "product_detail", nullable = true, length = 1000)
    private String productDetail;

    //소비자가
    @Column(name = "retail_price", nullable = false, length = 7)
    private Integer retailPrice;

    //판매가
    @Column(name = "sale_price", nullable = false, length = 7)
    private Integer salePrice;

    //유형
    @Column(name = "type", nullable = true, length = 10)
    private String type;

    //주의사항
    @Column(name = "caution", nullable = true, length = 1000)
    private String caution;

    //노출유무
    //@Builder.Default: 디폴트값 설정
    @Column(name = "is_visible", nullable = false, length = 1)
    @Builder.Default
    private Boolean isVisible = Boolean.TRUE;

    //제조사
    @Column(name = "manufacture", nullable = false, length = 50)
    private String manufacture;

    //브랜드
    @Column(name = "brand", nullable = true, length = 50)
    private String brand;

    //원산지
    @Column(name = "origin", nullable = false, length = 20)
    private String origin;

    //원료명
    @Column(name = "material", nullable = false, length = 50)
    private String material;

    //카테고리
    @Column(name = "category", nullable = false, length = 4)
    private String category;

    //판매회사명
    @Column(name = "sale_company", nullable = false, length = 50)
    private String saleCompany;

    //판매회사정보
    @Column(name = "sale_company_info", nullable = true, length = 1000)
    private String saleCompanyInfo;

    //삭제유무
    @Column(name = "is_deleted", nullable = false, length = 1)
    @Builder.Default
    private Boolean isDeleted = false;

    //품절유무
    @Column(name = "is_soldout", nullable = false, length = 1)
    @Builder.Default
    private Boolean isSoldout = Boolean.FALSE;

    //영양성분
    @Column(name = "nutrition", nullable = true, length = 1000)
    private String nutrition;

    //상품수량
    @Column(name = "quantity", nullable = false, length = 7)
    private Integer quantity;

    //상품포장수량
    @Column(name = "package_quatnity", nullable = true, length = 7)
    private Integer packageQuantity;

    //소비기한
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    //제조년월일
    @Column(name = "manufacture_date", nullable = false)
    private LocalDate manufactureDate;

    //유전자변형유무
    @Column(name = "is_gmo", nullable = false, length = 1)
    @Builder.Default
    private Boolean isGmo = Boolean.FALSE;

    //용량
    @Column(name = "volume", nullable = true, length = 3)
    private Integer volume;

    //관심상품카운트
    @Column(name = "wish_count", nullable = false, length = 7)
    @Builder.Default
    private Integer wishCount = Integer.valueOf(0);

    //등록일
    //@CreatedDate: 엔티티가 처음 생성될 때의 타임스탬프를 자동으로 기록
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    //수정일
    //@LastModifiedDate: 엔티티가 마지막으로 수정될 때의 타임스탬프를 자동으로 기록
    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    private LocalDate updatedDate;

    //productImages 매핑 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

    //subscribes 매핑 설정
    @OneToMany(mappedBy = "product")
    private List<Subscribe> subscribes;

    // 장바구니 테이블 (cart) 매핑 설정
    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    // QandA 테이블 (qanda) 매핑 설정
    @OneToMany(mappedBy = "product")
    private List<Qanda> qandas;

    // 구매후기 테이블 매핑 설정
    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews;

    // 구매상품 테이블 매핑 설정
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    // 관심상품 테이블 매핑 설정
    @OneToMany(mappedBy = "product")
    private List<WishList> wishLists;

}
