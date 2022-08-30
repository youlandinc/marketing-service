package com.youland.markting.config.base.callback;

import com.youland.markting.config.base.dataobj.CommonResult;

public interface ExtCallBack<T, R> extends BaseCallBack<T, R> {


    CommonResult<R> initResult();


    void call(T request, CommonResult<R> result);
}
