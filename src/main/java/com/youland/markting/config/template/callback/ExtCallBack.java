/**
 * * Youland.com copyright
 */
package com.youland.markting.config.template.callback;

import com.youland.markting.config.template.dataobj.CommonResult;

public interface ExtCallBack<T, R> extends BaseCallBack<T, R> {


    CommonResult<R> initResult();


    void call(T request, CommonResult<R> result);
}
