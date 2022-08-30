package com.youland.markting.config.base.callback;

import com.youland.markting.config.base.dataobj.BaseRequest;
import com.youland.markting.config.base.dataobj.CommonResult;

public interface ExeCallBack<T extends BaseRequest, R> extends  BaseCallBack<T, R> {


	void validate(T request);

	void execute(T request, CommonResult<R> result);

	default void postProcess(T Request, CommonResult<R> result) {};
}
