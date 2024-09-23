package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> ,
        JpaSpecificationExecutor<Cart> {

}
