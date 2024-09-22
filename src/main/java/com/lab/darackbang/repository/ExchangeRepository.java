package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> ,
        JpaSpecificationExecutor<Exchange> {
}