package com.youland.markting.config.template;

import cn.hutool.extra.spring.SpringUtil;
import com.youland.markting.config.template.callback.ExeCallBack;
import com.youland.markting.config.template.dataobj.BaseRequest;
import com.youland.markting.config.template.dataobj.CommonResult;
import com.youland.markting.config.template.enums.ErrorCode;
import com.youland.markting.config.template.enums.LogTypeEnum;
import com.youland.markting.config.template.exception.BizExecuteException;
import com.youland.markting.config.template.tool.AssertUtil;
import com.youland.markting.config.template.tool.LogPrinter;
import com.youland.markting.config.template.tool.WebResultBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author yeqiu
 * 2022/8/24 pm1:32:10
 */
public class TxBizTemplate {

    public static <T extends BaseRequest, R> CommonResult<R> execute(T request, ExeCallBack<T, R> callback) {

        try {
            LogPrinter.printApiBoundry("【tx api request start】【"+request.getOperateCode()+"】" + request.toString());
            if (request.getOperateCode() == null) {
                throw new BizExecuteException(ErrorCode.SYSTEM_ERROR, "operate code can't be null");
            }

            callback.validate(request);

            var transactionTemplate = SpringUtil.getBean(TransactionTemplate.class);
            AssertUtil.notNull(transactionTemplate, ErrorCode.SYSTEM_CONFIG_ERROR, "TransactionTemplate not exist");

            CommonResult<R> result = transactionTemplate.execute(new TransactionCallback<CommonResult<R>>() {

                @Override
                public CommonResult<R> doInTransaction(TransactionStatus status) {
                	var result = new CommonResult<R>(true);
                    callback.execute(request, result);
                    
                    return result;
                }
            });

            callback.postProcess(request, result);
            LogPrinter.printApiBoundry("【tx api request end】【"+request.getOperateCode()+"】" + result);
            return result;
        } catch (BizExecuteException e) {
            LogPrinter.printApiBoundry("【tx api biz exception】" + e.getErrMsg(), e);
            if (e.getErrCode() == ErrorCode.REPEAT_REQUEST) {
                return WebResultBuilder.buildRepeatSuccessResult();
            } else {
                return WebResultBuilder.buildErrorResult(e.getErrCode(), e.getErrMsg());
            }
        } catch (Throwable ex) {
            LogPrinter.printApiBoundry("【tx api sys error】" + ex.getMessage(), ex);
            return WebResultBuilder.buildSysErrorResult(ExceptionUtils.getStackTrace(ex));
        } finally {
        	LogPrinter.flushTarget(LogTypeEnum.API_BOUNDRY);
		}
    }
}
