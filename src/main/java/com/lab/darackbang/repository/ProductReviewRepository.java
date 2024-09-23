package com.lab.darackbang.repository;

import com.lab.darackbang.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>,
        JpaSpecificationExecutor<ProductReview> {
}