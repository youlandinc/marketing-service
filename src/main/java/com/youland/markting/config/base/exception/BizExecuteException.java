package com.youland.markting.config.base.exception;

import java.text.MessageFormat;

import com.youland.commons.exception.BaseException;
import com.youland.markting.config.base.enums.ErrorCode;

import lombok.Getter;

@Getter
public class BizExecuteException extends BaseException {

	/**
	 * error code 
	 */
	private ErrorCode errCode;
	
	/**
	 * error msg
	 */
	private String errMsg;
	
	/**
	 * exception stack
	 */
	private Throwable e;
	
	/**
	 * i18n param
	 */
	private Object[] args;

	public BizExecuteException(ErrorCode errorCode, Object... args) {
		super(errorCode.getCode(), MessageFormat.format(errorCode.getErrorMessage(), args));
		this.errCode = errorCode;
		this.errMsg = errorCode.getErrorMessage();
	}
	
	public BizExecuteException(ErrorCode errorCode, String errorMsg) {
		super(errorCode.getCode(), errorMsg);
		this.errCode = errorCode;
		this.errMsg = errorMsg;
	}
	
	public BizExecuteException(ErrorCode errorCode, String errorMsg, Object... args) {
		super(errorCode.getCode(), MessageFormat.format(errorMsg, args));
		this.errCode = errorCode;
		this.errMsg = errorMsg;
		this.args = args;
	}
	
	public BizExecuteException(ErrorCode errorCode, String errorMsg, Throwable e, Object... args) {
		super(errorCode.getCode(), MessageFormat.format(errorCode.getErrorMessage(), args), e);
		this.errCode = errorCode;
		this.errMsg = errorMsg;
		this.e = e;
	}
	
	public String getLocalizedMessage() {
        return this.errCode.getLocalizedMessage(this.args);
    }
}
