package com.youland.markting.config.base.callback;

public interface BaseCallBack<T, R> {

	default void preProcess(T request) {}
	
	default void postProcess(T request, R result) {}
}
