package com.youland.markting.config.base.enums;

import com.youland.commons.constant.BusinessExceptionAssert;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode implements BusinessExceptionAssert {

	SUCCESS("SUCCESS", "process success"),
	SYSTEM_ERROR("SYSTEM_ERROR", "system error"),
	SYSTEM_CONFIG_ERROR("SYSTEM_CONFIG_ERROR", "system config error"),
	PARAM_ILLEGAL("PARAM_ILLEGAL", "param illegal"),
	REPEAT_REQUEST("REPEAT_REQUEST", "repeat request"),
	UNKOWN_ERROR("UNKOWN_ERROR", "unkown exception"),
	TOKEN_EXPIRED("TOKEN_EXPIRED", "token expired"),
	ACCOUNT_DISABLED("ACCOUNT_DISABLED", "account disabled,please to input email verify code"),
	PASSWORD_INCORRECT("PASSWORD_INCORRECT", "password incorrect"),
	USER_INFO_NOT_FIND("USER_INFO_NOT_FIND", "user info not find"),
	ILLEGAL_REQUEST("ILLEGAL_REQUEST", "illegal request"),
	TOKEN_PARSE_ERROR("TOKEN_PARSE_ERROR", "token parse error"),
	TOKEN_GENERATE_ERROR("TOKEN_GENERATE_ERROR", "token create error"),
	BIZ_ERROR("BIZ_ERROR", "biz processor exception"),
	CHANGE_EMAIL_FAIL("CHANGE_EMAIL_FAIL", "change email fail"),
	CHANGE_PASS_FAIL("CHANGE_PASS_FAIL", "change password fail"), 
	NOT_FOUND("NOT_FOUND", "{0} not found"),
	SYSTEM_CONFIG_NOT_FOUND("SYSTEM_CONFIG_NOT_FOUND", "system config not found"),
	SAL_GOOGLE_EXCPTION("SAL_GOOGLE_EXCPTION", "sal google exception"),
	ERR_SEQUENCE("ERR_SEQUENCE", "error sequence"),
	UPDATE_RECORD_FAIL("UPDATE_RECORD_FAIL", "update db record fail"),
	AES_ENCRYPT_ERROR("AES_ENCRYPT_ERROR", "aes encrypt error"),
	AES_DECRYPT_ERROR("AES_DECRYPT_ERROR", "aes decrypt error"),
	RSA_SIGN_ERROR("RSA_SIGN_ERROR", "rsa sign error"),
	RSA_VERIFY_SIGN_ERROR("RSA_VERIFY_SIGN_ERROR", "rsa verify sign error"),
	RSA_ENCRYPT_ERROR("RSA_ENCRYPT_ERROR", "rsa encrypt error"),
	RSA_DECRYPT_ERROR("RSA_DECRYPT_ERROR", "rsa decrypt error"),
	RSA_VERIFY_SIGN_FAIL("RSA_VERIFY_SIGN_FAIL", "rsa verify sign fail"),
	JSON_PARSE_ERROR("JSON_PARSE_ERROR", "json parse error"),
	URL_ENCODE_ERROR("URL_ENCODE_ERROR", "url encode error"),
	
	;
	
	/**
	 * error code
	 */
	private String code;
	
	/**
	 * error msg
	 */
	private String msg;

	@Override
	public String getErrorCode() {
		return this.getCode();
	}

	@Override
	public String getErrorMessage() {
		return this.getMsg();
	}
	
}
