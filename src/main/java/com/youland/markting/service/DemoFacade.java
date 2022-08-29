package com.youland.markting.service;

import com.youland.markting.controller.standard.request.DemoRequest;
import com.youland.markting.controller.standard.response.DemoResponse;
import com.youland.markting.config.template.dataobj.CommonResult;

public interface DemoFacade {

	/**
	 * demo method
	 */
	CommonResult<DemoResponse> test(DemoRequest testRequest);

}
