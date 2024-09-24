package com.lab.darackbang.repository;

import com.lab.darackbang.entity.AgeQuarterStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgeQuarterStatRepository extends JpaRepository<AgeQuarterStat, Long>,
        JpaSpecificationExecutor<AgeQuarterStat> {

    Optional<AgeQuarterStat> findByQuarterAndYearAndAgeGroup(String quarter, String year, String ageGroup);
}