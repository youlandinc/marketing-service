package com.youland.markting.service.request;

import com.youland.markting.config.template.dataobj.BaseRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class DemoRequest extends BaseRequest  {


    private String procInstId;
}
