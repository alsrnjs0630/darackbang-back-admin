package com.lab.darackbang.repository;

import com.lab.darackbang.entity.ProductReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductReviewImageRepository extends JpaRepository<ProductReviewImage, Long> ,
        JpaSpecificationExecutor<ProductReviewImage> {
}