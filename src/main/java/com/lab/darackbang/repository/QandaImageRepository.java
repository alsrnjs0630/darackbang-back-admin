package com.lab.darackbang.repository;

import com.lab.darackbang.entity.QandaImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QandaImageRepository extends JpaRepository<QandaImage, Long>,
        JpaSpecificationExecutor<QandaImage> {
}