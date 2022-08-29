package com.youland.markting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youland.markting.dao.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	UserEntity findByTenantIdAndEmailAndLoginType(String tenantId, String email, String loginType);

	UserEntity findByTenantIdAndId(String tenantId, String userId);

}
