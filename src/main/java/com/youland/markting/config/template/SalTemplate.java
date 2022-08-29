package com.youland.markting.config.template;

import com.youland.markting.config.template.callback.SalCallBack;
import com.youland.markting.config.template.exception.BizExecuteException;
import com.youland.markting.config.template.tool.LogPrinter;

/**
 * 
 * @author yeqiu
 * 2022/8/16 pm3:24:23
 * integration boundary template
 */
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
