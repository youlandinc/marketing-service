package com.youland.markting.config.base;


import com.youland.markting.config.base.callback.ExeCallBack;
import com.youland.markting.config.base.dataobj.BaseRequest;
import com.youland.markting.config.base.dataobj.CommonResult;
import com.youland.markting.config.base.enums.ErrorCode;
import com.youland.markting.config.base.enums.LogTypeEnum;
import com.youland.markting.config.base.exception.BizExecuteException;
import com.youland.markting.config.base.tool.LogPrinter;
import com.youland.markting.config.base.tool.WebResultBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class BizTemplate {

    public static <T extends BaseRequest, R> CommonResult<R> execute(T request, ExeCallBack<T, R> callback) {
        try {
            LogPrinter.printApiBoundry("【api req begin】【"+request.getOperateCode()+"】" + request.toString());
            if (request.getOperateCode() == null) {
                throw new BizExecuteException(ErrorCode.SYSTEM_ERROR, "【"+request.getOperateCode()+"】operate code can't be null");
            }
            callback.validate(request);

            var result = new CommonResult<R>(true);
            callback.execute(request, result);

            callback.postProcess(request, result);
            LogPrinter.printApiBoundry("【api req end】【"+request.getOperateCode()+"】" + result);
            return result;
        } catch (BizExecuteException e) {
            LogPrinter.printApiBoundry("【api biz exception】" + e.getErrMsg(), e);
            if (e.getErrCode() == ErrorCode.REPEAT_REQUEST) {
                return WebResultBuilder.buildRepeatSuccessResult();
            } else {
                return WebResultBuilder.buildErrorResult(e.getErrCode(), e.getErrMsg());
            }
        } catch (Throwable ex) {
            LogPrinter.printApiBoundry("【api sys error】" + ex.getMessage(), ex);
            return WebResultBuilder.buildSysErrorResult(ExceptionUtils.getStackTrace(ex));
        } finally {
			LogPrinter.flushTarget(LogTypeEnum.API_BOUNDRY);
		}
    }
}
