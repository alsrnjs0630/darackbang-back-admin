package com.lab.darackbang.repository;

import com.lab.darackbang.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>,
        JpaSpecificationExecutor<ProductImage> {

    List<ProductImage> findByProductId(Long productId);
}
