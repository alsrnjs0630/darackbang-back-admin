package com.lab.darackbang.controller;

import com.lab.darackbang.dto.statistic.AgeStatisticDTO;
import com.lab.darackbang.dto.statistic.ProductStatisticDTO;
import com.lab.darackbang.dto.statistic.StatisticDTO;
import com.lab.darackbang.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("admin/statistics")
@RestController
@RequiredArgsConstructor
@Slf4j
public class StatisticController {

    private final StatisticService statisticService;


    @GetMapping("/age/total")
    public List<AgeStatisticDTO> getAgeGroupTotal() {
        return statisticService.getAgeStatistic();
    }

    @GetMapping("/product/total")
    public List<ProductStatisticDTO> getProductTotal() {
        return statisticService.getProductTotalStatistic();
    }

    @GetMapping("/product/year/{year}")
    public List<ProductStatisticDTO> getProductYear(@PathVariable String year) {
        return statisticService.getProductYearStatistic(year);
    }


    @GetMapping("/product/quarter/{year}/{quarter}")
    public List<ProductStatisticDTO> getProductQuarter(@PathVariable String year, @PathVariable String quarter) {
        return statisticService.getProductQuarterStatistic(year, quarter);
    }

    @GetMapping("/product/month/{year}/{month}")
    public List<ProductStatisticDTO> getProductMonth(@PathVariable String year, @PathVariable String month) {
        return statisticService.getProductMonthStatistic(year,month);
    }

    @GetMapping("/age/year/{year}")
    public List<AgeStatisticDTO> getAgeGroupYear(@PathVariable String year) {
        return statisticService.getAgeYearStatistic(year);
    }

    @GetMapping("/age/quarter/{year}/{quarter}")
    public List<AgeStatisticDTO> getAgeGroupYear(@PathVariable String year, @PathVariable String quarter) {
        return statisticService.getAgeQuarterStatistic(year, quarter);
    }

    @GetMapping("/age/month/{year}/{month}")
    public List<AgeStatisticDTO> getAgeGroupMonth(@PathVariable String year,@PathVariable String month) {
        return statisticService.getAgeMonthStatistic(year,month);
    }

    @GetMapping("/all")
    public StatisticDTO getStatistic() {
        return statisticService.getAllStatistic();
    }

}

