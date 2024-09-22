package com.lab.darackbang.repository;

import com.lab.darackbang.entity.AgeQuarterStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeQuarterStatRepository extends JpaRepository<AgeQuarterStat, Long>,
        JpaSpecificationExecutor<AgeQuarterStat> {
}