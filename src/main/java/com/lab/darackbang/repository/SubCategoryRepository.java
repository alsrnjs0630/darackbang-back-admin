package com.lab.darackbang.repository;

import com.lab.darackbang.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, String>,
        JpaSpecificationExecutor<SubCategory> {
}