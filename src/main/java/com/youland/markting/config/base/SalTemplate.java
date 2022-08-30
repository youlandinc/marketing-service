package com.youland.markting.config.base;

import com.youland.markting.config.base.callback.SalCallBack;
import com.youland.markting.config.base.exception.BizExecuteException;
import com.youland.markting.config.base.tool.LogPrinter;

public class SalTemplate {

    public static <T, R> R invoke(T request, SalCallBack<T, R> callback) {
        try {
            LogPrinter.printSalBoundry("【cilent req begin】" + request.toString());

            callback.preProcess(request);

            R result = callback.invoke(request);

            callback.postProcess(request, result);

            LogPrinter.printSalBoundry("【cilent req end】result=" + result);
            return result;
        } catch (BizExecuteException e) {
            LogPrinter.printSalBoundry("【cilent biz exception】" + e.getErrMsg(), e);
            throw e;
        } catch (Throwable ex) {
            LogPrinter.printSalBoundry("【cilent sys error】" + ex.getMessage(), ex);
            throw ex;
        }
    }
    
}
