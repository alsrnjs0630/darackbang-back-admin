package com.lab.darackbang.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * A DTO for the {@link com.lab.darackbang.entity.Member} entity.
 */
@Schema(description = "회원정보")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 인덱스번호
     */
    @NotNull
    @Schema(description = "인덱스번호", required = true)
    private Long id;

    /**
     * 회원로그인ID
     */
    @NotNull
    @Size(max = 120)
    @Schema(description = "회원로그인ID",requiredMode = Schema.RequiredMode.REQUIRED)
    private String userEmail;

    // 패스워드 ( not null, 크기 16, 특수문자 1개 이상 + 숫자 1개 이상 + 대소문자 구분 영문자 8자 이상 ~ 16자 이하 )
    @NotNull
    @Size(max = 255)
    @Schema(description = "패스워드",requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    /**
     * 회원이름
     */
    @NotNull
    @Size(max = 15)
    @Schema(description = "회원이름",requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * 생년월일
     */
    @Size(max = 10)
    @Schema(description = "생년월일",requiredMode = Schema.RequiredMode.REQUIRED)
    private String birthday;
    /**
     * 연령대
     */
    @Size(max = 2)
    @Schema(description = "연령대", required = true)
    private String ageGroup;
    /**
     * 휴대폰번호
     */
    @Size(max = 1)
    @Schema(description = "성별",requiredMode = Schema.RequiredMode.REQUIRED)
    private String gender;
    /**
     * 휴대폰번호
     */
    @Size(max = 11)
    @Schema(description = "휴대폰번호",requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobileNo;

    /**
     * 전화번호
     */
    @Size(max = 11)
    @Schema(description = "전화번호",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phoneNo;


    /**
     * 주소
     */
    @Size(max = 150)
    @Schema(description = "주소",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String address;

    /**
     * 우편번호
     */
    @Size(max = 5)
    @Schema(description = "우편번호",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String postNo;
    /**
     * 기본배송지
     */
    @Size(max = 150)
    @Schema(description = "기본배송지",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String shippingAddr;

    /**
     * 기본우편번호
     */
    @Size(max = 5)
    @Schema(description = "기본우편번호",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String shipPostNo;

    /**
     * 추가배송지
     */
    @Size(max = 150)
    @Schema(description = "추가배송지",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String addShippingAddr;

    /**
     * 추가우편번호
     */
    @Size(max = 5)
    @Schema(description = "추가우편번호",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String addPostNo;

    /**
     * 마일리지
     */
    @NotNull
    @Size(max = 255)
    @Schema(description = "가입설문조사여부",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer mileage;


    /**
     * 삭제여부
     */
    @NotNull
    @Schema(description = "삭제여부",requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isDeleted;

    /**
     * 블랙컨슈머유무
     */
    @NotNull
    @Schema(description = "블랙컨슈머유무",requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isBlacklist;

    // 회원상태 (default 01 정상 : 02 탈퇴 : 03 탈퇴신청)
    @NotNull
    @Size(max = 2)
    @Schema(description = "회원상태",requiredMode = Schema.RequiredMode.REQUIRED)
    private String memberState;

    /**
     * 등록일시
     */
    @NotNull
    @Schema(description = "등록일시",requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate createdDate;

    /**
     * 수정일시
     */
    @NotNull
    @Schema(description = "수정일시",requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate updatedDate;

}
