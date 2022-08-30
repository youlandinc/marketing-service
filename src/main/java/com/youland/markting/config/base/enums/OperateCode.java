package com.youland.markting.config.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperateCode {

	DEFAULT("DEFAULT", "default operate"),
	CREATE_TENANT("CREATE_TENANT", "create tenant"),
	USER_REGISTER("USER_REGISTER", "user create account in tenant"),
	VERIFY_CODE("VERIFY_CODE", "verify email send code"),
	USER_LOGIN("USER_LOGIN", "user login in system"),
	SEND_CODE("SEND_CODE", "send email verify code"),
	RESET_PASSWORD("RESET_PASSWORD", "reset user password"),
	CHANGE_EMAIL("CHANGE_EMAIL", "reset user email"),
	CHANGE_PASSWORD("CHANGE_PASSWORD", "reset user password"),
	MODIFY_USER_INFO("MODIFY_USER_INFO", "modify user info"), 
	EMAIL_INVITE_USER("EMAIL_INVITE_USER", "email invite user join"), 
	
	;
	
	private String code;
	
	private String desc;
}
