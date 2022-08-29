package com.youland.markting.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum AccountStatusEnum {

	ENABLED(1, "enable to use"),
	DISABLED(0, "disenable to use");
	
	private int code;
	
	private String desc;
}
