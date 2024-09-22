package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Qanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QandaRepository extends JpaRepository<Qanda, Long>,
        JpaSpecificationExecutor<Qanda> {
}