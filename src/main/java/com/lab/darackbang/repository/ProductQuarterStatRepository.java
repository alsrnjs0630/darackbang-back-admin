package com.lab.darackbang.repository;

import com.lab.darackbang.entity.ProductQuarterStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductQuarterStatRepository extends JpaRepository<ProductQuarterStat, Long>,
        JpaSpecificationExecutor<ProductQuarterStat> {

    Optional<ProductQuarterStat> findByQuarterAndYearAndProductName(String quarter, String year, String productName);

}