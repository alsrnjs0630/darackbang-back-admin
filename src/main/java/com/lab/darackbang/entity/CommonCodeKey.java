package com.lab.darackbang.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonCodeKey implements Serializable {
    private String commonCode;
    private String commonGroupCode;
}
