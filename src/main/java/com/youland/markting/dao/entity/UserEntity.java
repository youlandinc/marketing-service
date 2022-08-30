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
@Table(name = "yl_oss_user")
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
	 * user name
	 */
	@Column(length = 64)
	private String userName;


	/**
	 * user email
	 */
	@Column(length = 32)
	private String email;

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
