package com.youland.markting.config.base.dataobj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TraceId {

	private String id;
	
	private long startTime;
	
	private long endTime;
}
