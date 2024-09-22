package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Delivery;
import com.lab.darackbang.entity.ProductYearStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductYearStatRepository extends JpaRepository<ProductYearStat, Long> ,
        JpaSpecificationExecutor<ProductYearStat> {
}