package com.lab.darackbang.repository;

import com.lab.darackbang.entity.ImageAnalyze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageAnalyzeRepository extends JpaRepository<ImageAnalyze, Long>,
        JpaSpecificationExecutor<ImageAnalyze> {
}