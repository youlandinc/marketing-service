package com.youland.markting.controller.standard.request;

import com.youland.markting.config.base.dataobj.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DemoRequest extends BaseRequest {

	private String procInstId;
}
