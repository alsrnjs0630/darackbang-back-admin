package com.lab.darackbang.repository;

import com.lab.darackbang.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long>,
        JpaSpecificationExecutor<OrderHistory> {
}