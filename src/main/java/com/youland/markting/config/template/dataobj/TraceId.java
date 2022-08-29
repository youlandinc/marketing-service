package com.youland.markting.config.template.dataobj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* yeqiu 2022年7月26日 下午4:46:31
* 链路追查
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TraceId {

	private String id;
	
	private long startTime;
	
	private long endTime;
}
