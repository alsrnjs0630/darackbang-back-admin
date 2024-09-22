package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Delivery;
import com.lab.darackbang.entity.ProductQuarterStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductQuarterStatRepository extends JpaRepository<ProductQuarterStat, Long>,
        JpaSpecificationExecutor<ProductQuarterStat> {
}