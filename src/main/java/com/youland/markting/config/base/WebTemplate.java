package com.youland.markting.config.base;

import com.youland.markting.config.base.callback.ExtCallBack;
import com.youland.markting.config.base.dataobj.CommonResult;
import com.youland.markting.config.base.exception.BizExecuteException;
import com.youland.markting.config.base.tool.LogPrinter;

public class WebTemplate {

    public static <R, T> T execute(R request, ExtCallBack<R,T> callback) {
        var trace = LogPrinter.fetchTrace();
        try {
            LogPrinter.printWebBoundry("【web client req】【" + trace.getId() + "】" + request.toString());
            
            CommonResult<T> result = callback.initResult();
            callback.call(request, result);
            
            LogPrinter.printWebBoundry("【web client result】【" + trace.getId() + "】" + result);
            return result.extract();
        } catch (Throwable e) {
            if (e instanceof BizExecuteException) {
            	LogPrinter.printWebBoundry("【web client Biz Exception】【" + trace.getId() + "】" + ((BizExecuteException) e).getErrMsg(), e);
            } else {
            	LogPrinter.printWebBoundry("【web client sys error】【" + trace.getId() + "】" + e.getMessage(), e);
            }
            throw e;
        } finally {
            LogPrinter.flush();
        }
    }
}
