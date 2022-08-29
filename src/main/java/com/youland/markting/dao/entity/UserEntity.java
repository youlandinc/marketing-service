package com.youland.markting.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "yl_oss_user", uniqueConstraints = @UniqueConstraint(columnNames= {"tenantId", "email", "loginType"}))
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

	/**
	 * primary key
	 */
	@Id
	@Column(nullable = false, columnDefinition = "varchar", length = 64)
	private String id;
	
	/**
	 * tenant ID
	 */
	@Column(length = 64, nullable = false)
	private String tenantId;
	
	/** 
	 * user name
	 */
	@Column(length = 64)
	private String userName;
	
	/**
	 * user account
	 */
	@Column(length = 64, nullable = false)
	private String account;
	
	/**
	 * user email
	 */
	@Column(length = 32)
	private String email;
	
	/**
	 * tenant type
	 * CUSTOMER/BROKER/SYSMNG
	 */
	@Column(length = 32, nullable = false)
	private String userType;
	
	/**
	 * register&login type
	 */
	@Column(length = 32, nullable = false)
	private String loginType;
	
	/**
	 * user head photo
	 */
	@Column(length = 512)
	private String avatar;
	
	/**
	 * user address
	 */
	@Column(length = 512)
	private String address;
	
	/**
	 * user telephone
	 */
	@Column(length = 32)
	private String telephone;
	
	/**
	 * create Time
	 */
	private LocalDateTime gmtCreate;
	
	/**
	 * modified time
	 */
	private LocalDateTime gmtModified;
	
	/**
	 * creator
	 */
	@Column(columnDefinition = "varchar", length = 100)
	private String creator;
	
	/**
	 * modified operator;
	 */
	@Column(columnDefinition = "varchar", length = 100)
	private String editor;
	
}
