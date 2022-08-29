package com.youland.markting.controller.standard.response;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoResponse {

	/**
	 * userId;
	 */
	private String userId;
	
	/**
	 * user tenant
	 */
	private String tenantId;
	
	/**
	 * user name
	 */
	private String name;
	
	/**
	 * avator photo
	 */
	private String avatar;
	
	/**
	 * user account
	 */
	private String account;
}
