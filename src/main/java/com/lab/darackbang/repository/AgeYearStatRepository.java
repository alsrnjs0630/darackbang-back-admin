package com.lab.darackbang.repository;

import com.lab.darackbang.entity.AgeYearStat;
import com.lab.darackbang.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgeYearStatRepository extends JpaRepository<AgeYearStat, Long> ,
        JpaSpecificationExecutor<AgeYearStat> {
}