package com.youland.markting.config.base.callback;

public interface SalCallBack<T,R> extends BaseCallBack<T, R> {

    R invoke(T request);
}
