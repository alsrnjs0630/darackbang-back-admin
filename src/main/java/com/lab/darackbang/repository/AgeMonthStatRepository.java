package com.lab.darackbang.repository;

import com.lab.darackbang.entity.AgeMonthStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgeMonthStatRepository extends JpaRepository<AgeMonthStat, Long>,
        JpaSpecificationExecutor<AgeMonthStat> {

    Optional<AgeMonthStat> findByMonthAndYearAndAgeGroup(String month, String year, String ageGroup);

    List<AgeMonthStat> findAllByYearAndMonth(String year,String month);

}