package com.lab.darackbang.repository;

import com.lab.darackbang.entity.AgeMonthStat;
import com.lab.darackbang.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgeMonthStatRepository extends JpaRepository<AgeMonthStat, Long>,
        JpaSpecificationExecutor<AgeMonthStat> {
}