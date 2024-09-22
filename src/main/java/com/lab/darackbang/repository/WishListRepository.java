package com.lab.darackbang.repository;

import com.lab.darackbang.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long>, JpaSpecificationExecutor<WishList> {
}
