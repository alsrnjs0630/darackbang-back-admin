package com.lab.darackbang.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgeStatisticDTO {

    private String ageGroup;

    private Integer totalAmount;
}
