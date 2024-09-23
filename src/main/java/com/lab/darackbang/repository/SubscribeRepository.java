package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long>, JpaSpecificationExecutor<Subscribe> {
}
