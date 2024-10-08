package com.lab.darackbang.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberSearchDTO {
    private String userEmail;
    private String name;
    private String gender;
    private String phoneNo;
    private Boolean isBlacklist;
    private String memberState;
}
