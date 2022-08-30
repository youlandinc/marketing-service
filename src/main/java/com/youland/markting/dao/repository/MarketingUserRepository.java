package com.youland.markting.dao.repository;

import com.youland.markting.dao.entity.MarketingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chenning
 */
@Repository
public interface MarketingUserRepository extends JpaRepository<MarketingUser, Long> {
}
