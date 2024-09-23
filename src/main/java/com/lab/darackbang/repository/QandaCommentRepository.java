package com.lab.darackbang.repository;

import com.lab.darackbang.entity.QandaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QandaCommentRepository extends JpaRepository<QandaComment, Long> ,
        JpaSpecificationExecutor<QandaComment> {
}