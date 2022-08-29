/**
** Youland.com copyright
*/
package com.youland.markting.config.template.callback;
/**
* yeqiu 
* 2022/7/26 pm2:53:51
* biz call back interface
*/
public interface BaseCallBack<T, R> {

	default void preProcess(T request) {}
	
	default void postProcess(T request, R result) {}
}
