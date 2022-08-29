package com.youland.markting.config.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogTypeEnum {

	WEB_BOUNDRY("WEB_BOUNDRY_LOG","web boundary log"),
	API_BOUNDRY("API_BOUNDRY_LOG","API boundary log"),
	SAL_BOUNDRY("SAL_BOUNDRY_LOG","Client boundary log"),
	BIZERROR_BOUNDRY("BIZERROR_BOUNDRY_LOG","Exception boundary log"),
	TRACE_BOUNDRY("TRACE_BOUNDRY_LOG","Biz trace boundary log"),
	;
	
	private String code;
	
	private String desc;
}
