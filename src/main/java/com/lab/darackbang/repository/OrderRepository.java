package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Cart;
import com.lab.darackbang.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findByMemberId(Long memberId);
}