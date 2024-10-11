package com.lab.darackbang.service.statistic;

import com.lab.darackbang.dto.statistic.AgeStatisticDTO;
import com.lab.darackbang.dto.statistic.ProductStatisticDTO;
import com.lab.darackbang.dto.statistic.StatisticDTO;

import java.util.List;

public interface StatisticService {

    List<AgeStatisticDTO> getAgeStatistic();

    List<AgeStatisticDTO> getAgeYearStatistic(String year);

    StatisticDTO getAllStatistic();

    StatisticDTO getStatistic(String year, String quarter, String month) ;

    List<AgeStatisticDTO> getAgeQuarterStatistic(String year, String quarter);

    List<AgeStatisticDTO> getAgeMonthStatistic(String year, String month);

    List<ProductStatisticDTO> getProductYearStatistic(String year);

    List<ProductStatisticDTO> getProductQuarterStatistic(String year, String quarter);

    List<ProductStatisticDTO> getProductMonthStatistic(String year, String month);

    List<ProductStatisticDTO> getProductTotalStatistic();
}
