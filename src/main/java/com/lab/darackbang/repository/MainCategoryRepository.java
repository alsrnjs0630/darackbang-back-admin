package com.lab.darackbang.repository;

import com.lab.darackbang.entity.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, String>,
        JpaSpecificationExecutor<MainCategory> {
}