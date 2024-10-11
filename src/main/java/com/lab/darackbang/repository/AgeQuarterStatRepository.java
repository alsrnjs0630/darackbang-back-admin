package com.lab.darackbang.repository;

import com.lab.darackbang.entity.AgeMonthStat;
import com.lab.darackbang.entity.AgeQuarterStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgeQuarterStatRepository extends JpaRepository<AgeQuarterStat, Long>,
        JpaSpecificationExecutor<AgeQuarterStat> {

    Optional<AgeQuarterStat> findByQuarterAndYearAndAgeGroup(String quarter, String year, String ageGroup);

    List<AgeQuarterStat> findAllByYear(String year);

    List<AgeQuarterStat> findAllByYearAndQuarter(String year, String quarter);

}