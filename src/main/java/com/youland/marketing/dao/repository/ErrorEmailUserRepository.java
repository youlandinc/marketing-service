package com.youland.marketing.dao.repository;

import com.youland.marketing.dao.entity.ErrorEmailUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chenning
 */
@Repository
public interface ErrorEmailUserRepository extends JpaRepository<ErrorEmailUser, Long> {
}
