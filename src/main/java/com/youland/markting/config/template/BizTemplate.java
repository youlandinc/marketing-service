package com.youland.markting.config.template;


import com.youland.markting.config.template.callback.ExeCallBack;
import com.youland.markting.config.template.dataobj.BaseRequest;
import com.youland.markting.config.template.dataobj.CommonResult;
import com.youland.markting.config.template.enums.ErrorCode;
import com.youland.markting.config.template.enums.LogTypeEnum;
import com.youland.markting.config.template.exception.BizExecuteException;
import com.youland.markting.config.template.tool.LogPrinter;
import com.youland.markting.config.template.tool.WebResultBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author yeqiu
 * biz execute template
 */
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
