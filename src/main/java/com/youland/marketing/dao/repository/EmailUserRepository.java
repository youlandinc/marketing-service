package com.youland.marketing.dao.repository;

import com.youland.marketing.dao.entity.EmailUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author chenning
 */
@Repository
public interface EmailUserRepository extends JpaRepository<EmailUser, Long> {
    EmailUser findFirstByEmail(String email);

    @Query(value = "select u from EmailUser u where upper(u.email) like %?1%")
    EmailUser findFirstByEmailIgnoreCase(String email);
}
