package com.youland.markting.config.template.dataobj;

import java.util.UUID;

import com.youland.markting.config.template.enums.OperateCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class BaseRequest {

	private OperateCode operateCode;

	private String innerRequestId;
	
	public BaseRequest(){
		this.innerRequestId = UUID.randomUUID().toString().replaceAll("-", "");
	}
}
