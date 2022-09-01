package com.youland.marketing.dao.repository;

import com.youland.marketing.dao.entity.EmailUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chenning
 */
@Repository
public interface EmailUserRepository extends JpaRepository<EmailUser, Long> {
    EmailUser findFirstByEmail(String email);
}
