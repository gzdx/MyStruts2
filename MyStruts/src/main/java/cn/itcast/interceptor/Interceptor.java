package cn.itcast.interceptor;

import cn.itcast.invocation.ActionInvocation;

/**
 * Created by zhen on 2017-08-06.
 */
public interface Interceptor {
    public void init();
    public String invoke(ActionInvocation actionInvocation);
    public void destroy();
}
