package com.lab.darackbang.repository;

import com.lab.darackbang.entity.AgeQuarterStat;
import com.lab.darackbang.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgeQuarterStatRepository extends JpaRepository<AgeQuarterStat, Long>,
        JpaSpecificationExecutor<AgeQuarterStat> {
}