package com.youland.markting.config.template.callback;
/**
 * @author yeqiu
 * 2022/8/16 pm3:17:17
 */
public interface SalCallBack<T,R> extends BaseCallBack<T, R> {

    public R invoke(T request);
}
