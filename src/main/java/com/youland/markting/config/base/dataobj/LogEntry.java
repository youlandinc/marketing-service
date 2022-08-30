package com.youland.markting.config.base.dataobj;

import lombok.Data;

@Data
public class LogEntry {

	private String traceId;

	private String logContent;

	private String timestamp;

	private String costTime;

	private Throwable stacks;
	
}
