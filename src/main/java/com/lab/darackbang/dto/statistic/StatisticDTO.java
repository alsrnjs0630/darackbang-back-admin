package com.lab.darackbang.dto.statistic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class StatisticDTO {

    private List<AgeStatisticDTO> ageGroupTotalStatistic;

    private List<AgeStatisticDTO> ageGroupYearStatistic;

    private List<AgeStatisticDTO> ageGroupQuarterStatistic;

    private List<AgeStatisticDTO> ageGroupMonthStatistic;

    private List<ProductStatisticDTO> productTotalStatistic;

    private List<ProductStatisticDTO> productYearStatistic;

    private List<ProductStatisticDTO> productQuarterStatistic;

    private List<ProductStatisticDTO> productMonthStatistic;
}
