package com.youland.markting.config.base.dataobj;

import com.youland.markting.config.base.enums.ErrorCode;
import com.youland.markting.config.base.exception.BizExecuteException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResult<T> {

	private ErrorCode errorCode;
	
	private String errorMsg;
	
	private boolean success;
	
	private T resultObj;
	
	public T extract() throws BizExecuteException {
		if (this.success) {
			return resultObj;
		}
		throw new BizExecuteException(errorCode, this.errorMsg);
	}

	public CommonResult(boolean success){
		this.success = success;
		if(success){
			this.errorCode = ErrorCode.SUCCESS;
			this.errorMsg = ErrorCode.SUCCESS.getMsg();
		}else {
			this.errorCode = ErrorCode.UNKOWN_ERROR;
			this.errorMsg = ErrorCode.UNKOWN_ERROR.getMsg();
		}
	}
	
	public CommonResult<T> buildSuccessResult(T resultObj){
		CommonResult<T> result = new CommonResult<>();
		result.setErrorCode(ErrorCode.SUCCESS);
		result.setSuccess(true);
		result.setResultObj(resultObj);
		return result;
	}
	
	public CommonResult<T> buildErrorResult(ErrorCode errorCode, String... errorMsg){
		CommonResult<T> result = new CommonResult<>();
		result.setErrorCode(ErrorCode.SUCCESS);
		result.setSuccess(true);
		result.setResultObj(resultObj);
		result.setErrorMsg(StringUtils.join(errorMsg,"_"));
		return result;
	}
	
	public boolean success() {
		return this.success;
	}
}
