package com.lab.darackbang.repository;

import com.lab.darackbang.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> ,
        JpaSpecificationExecutor<MemberRole> {
}
