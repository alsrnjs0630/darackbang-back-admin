package com.lab.darackbang.service.statistic;

import com.lab.darackbang.dto.statistic.AgeStatisticDTO;
import com.lab.darackbang.dto.statistic.ProductStatisticDTO;
import com.lab.darackbang.dto.statistic.StatisticDTO;
import com.lab.darackbang.entity.*;
import com.lab.darackbang.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final AgeYearStatRepository ageYearStatRepository;

    private final AgeQuarterStatRepository ageQuarterStatRepository;

    private final AgeMonthStatRepository ageMonthStatRepository;

    private final ProductYearStatRepository productYearStatRepository;

    private final ProductQuarterStatRepository productQuarterStatRepository;

    private final ProductMonthStatRepository productMonthStatRepository;

    @Override
    public List<AgeStatisticDTO> getAgeStatistic() {

        List<AgeYearStat> ageTotalStatList = ageYearStatRepository.findAll();

        //Total 연령대별 총 구매금액
        return ageTotalStatList.stream()
                .collect(Collectors.groupingBy(AgeYearStat::getAgeGroup,
                        Collectors.summingInt(AgeYearStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new AgeStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();

    }

    @Override
    public List<AgeStatisticDTO> getAgeYearStatistic(String year) {
        //현재년도 연령별 통계
        List<AgeYearStat> ageYearStatList = ageYearStatRepository.findAllByYear(year);

        return ageYearStatList.stream()
                .collect(Collectors.groupingBy(AgeYearStat::getAgeGroup,
                        Collectors.summingInt(AgeYearStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new AgeStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public StatisticDTO getAllStatistic() {
        StatisticDTO statisticDTO = new StatisticDTO();

        String year = String.valueOf(LocalDate.now().getYear());
        String quarter = String.valueOf((LocalDate.now().getMonthValue() - 1) / 3 + 1);
        String month = String.valueOf(LocalDate.now().getMonthValue());


        List<AgeYearStat> ageTotalStatList = ageYearStatRepository.findAll();


        //Total 연령대별 총 구매금액
        List<AgeStatisticDTO> ageGroupTotals = ageTotalStatList.stream()
                .collect(Collectors.groupingBy(AgeYearStat::getAgeGroup,
                        Collectors.summingInt(AgeYearStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new AgeStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();

        statisticDTO.setAgeGroupTotalStatistic(ageGroupTotals);

        //현재년도 연령별 통계
        List<AgeYearStat> ageYearStatList = ageYearStatRepository.findAllByYear(year);

        List<AgeStatisticDTO> ageGroupYears = ageYearStatList.stream()
                .collect(Collectors.groupingBy(AgeYearStat::getAgeGroup,
                        Collectors.summingInt(AgeYearStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new AgeStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();

        statisticDTO.setAgeGroupYearStatistic(ageGroupYears);

        //해당년 현재 분기 연령별 통계
        List<AgeQuarterStat> AgeQuarterStatList = ageQuarterStatRepository.findAllByYearAndQuarter(year, quarter);


        List<AgeStatisticDTO> ageGroupQuarters = AgeQuarterStatList.stream()
                .collect(Collectors.groupingBy(AgeQuarterStat::getAgeGroup,
                        Collectors.summingInt(AgeQuarterStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new AgeStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();


        statisticDTO.setAgeGroupQuarterStatistic(ageGroupQuarters);

        //해당년 현재 월 연령별 통계
        List<AgeMonthStat> ageMonthStatList = ageMonthStatRepository.findAllByYearAndMonth(year, month);


        List<AgeStatisticDTO> ageGroupMonths = ageMonthStatList.stream()
                .collect(Collectors.groupingBy(AgeMonthStat::getAgeGroup,
                        Collectors.summingInt(AgeMonthStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new AgeStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();


        statisticDTO.setAgeGroupMonthStatistic(ageGroupMonths);

        List<ProductYearStat> productAllStatList = productYearStatRepository.findAll();

        //Total 상품별 총 구매금액
        List<ProductStatisticDTO> productTotals = productAllStatList.stream()
                .collect(Collectors.groupingBy(ProductYearStat::getProductName,
                        Collectors.summingInt(ProductYearStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new ProductStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();


        statisticDTO.setProductTotalStatistic(productTotals);


        List<ProductYearStat> productYearStatList = productYearStatRepository.findAllByYear(year);


        //Total 상품별 총 구매금액
        List<ProductStatisticDTO> productYears = productYearStatList.stream()
                .collect(Collectors.groupingBy(ProductYearStat::getProductName,
                        Collectors.summingInt(ProductYearStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new ProductStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();

        statisticDTO.setProductYearStatistic(productYears);

        List<ProductQuarterStat> productQuarterStatList = productQuarterStatRepository.findAllByYearAndQuarter(year, quarter);


        //Total 상품별 총 구매금액
        List<ProductStatisticDTO> productQuarters = productQuarterStatList.stream()
                .collect(Collectors.groupingBy(ProductQuarterStat::getProductName,
                        Collectors.summingInt(ProductQuarterStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new ProductStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();

        statisticDTO.setProductQuarterStatistic(productQuarters);

        List<ProductMonthStat> productMonthStatList = productMonthStatRepository.findAllByYearAndMonth(year, month);

        //Total 상품별 총 구매금액
        List<ProductStatisticDTO> productMonths = productMonthStatList.stream()
                .collect(Collectors.groupingBy(ProductMonthStat::getProductName,
                        Collectors.summingInt(ProductMonthStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new ProductStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();


        statisticDTO.setProductMonthStatistic(productMonths);

        return statisticDTO;
    }

    @Override
    public StatisticDTO getStatistic(String year, String quarter, String month) {

        return null;
    }

    @Override
    public List<AgeStatisticDTO> getAgeQuarterStatistic(String year, String quarter) {
        List<AgeQuarterStat> AgeQuarterStatList = ageQuarterStatRepository.findAllByYearAndQuarter(year, quarter);


        return AgeQuarterStatList.stream()
                .collect(Collectors.groupingBy(AgeQuarterStat::getAgeGroup,
                        Collectors.summingInt(AgeQuarterStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new AgeStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public List<AgeStatisticDTO> getAgeMonthStatistic(String year, String month) {
        //해당년 현재 월 연령별 통계
        List<AgeMonthStat> ageMonthStatList = ageMonthStatRepository.findAllByYearAndMonth(year, month);

        return ageMonthStatList.stream()
                .collect(Collectors.groupingBy(AgeMonthStat::getAgeGroup,
                        Collectors.summingInt(AgeMonthStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new AgeStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public List<ProductStatisticDTO> getProductYearStatistic(String year) {

        List<ProductYearStat> productYearStatList = productYearStatRepository.findAllByYear(year);

        //Total 상품별 총 구매금액
       return productYearStatList.stream()
                .collect(Collectors.groupingBy(ProductYearStat::getProductName,
                        Collectors.summingInt(ProductYearStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new ProductStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public List<ProductStatisticDTO> getProductQuarterStatistic(String year, String quarter) {
        List<ProductQuarterStat> productQuarterStatList = productQuarterStatRepository.findAllByYearAndQuarter(year, quarter);


        //Total 상품별 총 구매금액
        return productQuarterStatList.stream()
                .collect(Collectors.groupingBy(ProductQuarterStat::getProductName,
                        Collectors.summingInt(ProductQuarterStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new ProductStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public List<ProductStatisticDTO> getProductMonthStatistic(String year, String month) {
        List<ProductMonthStat> productMonthStatList = productMonthStatRepository.findAllByYearAndMonth(year, month);

        //Total 상품별 총 구매금액
       return productMonthStatList.stream()
                .collect(Collectors.groupingBy(ProductMonthStat::getProductName,
                        Collectors.summingInt(ProductMonthStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new ProductStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public List<ProductStatisticDTO> getProductTotalStatistic() {
        List<ProductYearStat> productAllStatList = productYearStatRepository.findAll();

        //Total 상품별 총 구매금액
      return productAllStatList.stream()
                .collect(Collectors.groupingBy(ProductYearStat::getProductName,
                        Collectors.summingInt(ProductYearStat::getSaleTotalPrice)))
                .entrySet().stream()
                .map(entry -> new ProductStatisticDTO(entry.getKey(), entry.getValue()))
                .toList();

    }
}
