package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Delivery;
import com.lab.darackbang.entity.ProductMonthStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductMonthStatRepository extends JpaRepository<ProductMonthStat, Long>,
        JpaSpecificationExecutor<ProductMonthStat> {
}