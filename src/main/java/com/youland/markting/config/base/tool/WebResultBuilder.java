package com.youland.markting.config.base.tool;

import com.youland.markting.config.base.dataobj.CommonResult;
import com.youland.markting.config.base.enums.ErrorCode;

public class WebResultBuilder {

	public static <T> CommonResult<T> buildErrorResult(ErrorCode errorCode, String errorMsg){
		CommonResult<T> result = new CommonResult<>(); 
		result.setErrorCode(errorCode);
		result.setErrorMsg(errorMsg);
		result.setSuccess(false);
		return result;
	}
	
	public static <T> CommonResult<T> buildSysErrorResult(String errorMsg){
		CommonResult<T> result = new CommonResult<>(); 
		result.setErrorCode(ErrorCode.SYSTEM_ERROR);
		result.setErrorMsg(errorMsg);
		result.setSuccess(false);
		return result;
	}
	
	public static <T> CommonResult<T> buildSuccessResult(T resultObj){
		CommonResult<T> result = new CommonResult<>(); 
		result.setErrorCode(ErrorCode.SUCCESS);
		result.setErrorMsg(ErrorCode.SUCCESS.getMsg());
		result.setSuccess(true);
		result.setResultObj(resultObj);
		return result;
	}
	
	public static <T> CommonResult<T> buildRepeatSuccessResult(){
		CommonResult<T> result = new CommonResult<>(); 
		result.setErrorCode(ErrorCode.REPEAT_REQUEST);
		result.setErrorMsg(ErrorCode.REPEAT_REQUEST.getMsg());
		result.setSuccess(true);
		return result;
	}
	
	public static <T> CommonResult<T> buildSuccessResult(){
		return buildSuccessResult(null);
	}
}
