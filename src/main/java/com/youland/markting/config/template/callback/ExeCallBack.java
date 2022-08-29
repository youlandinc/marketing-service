/**
** Youland.com copyright
*/
package com.youland.markting.config.template.callback;

import com.youland.markting.config.template.dataobj.BaseRequest;
import com.youland.markting.config.template.dataobj.CommonResult;

/**
* yeqiu 
* 2022/7/26 pm2:49:12
*/
public interface ExeCallBack<T extends BaseRequest, R> extends  BaseCallBack<T, R> {

	/**
	 * validate
	 */
	public void validate(T request);
	
	/**
	 * execute
	 */
	public void execute(T request, CommonResult<R> result);
	
	/**
	 * postProcess
	 * @param Request
	 * @return
	 */
	default void postProcess(T Request, CommonResult<R> result) {};
}
