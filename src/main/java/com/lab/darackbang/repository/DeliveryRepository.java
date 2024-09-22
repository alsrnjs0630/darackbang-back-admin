package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Cart;
import com.lab.darackbang.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>,
        JpaSpecificationExecutor<Delivery> {
}