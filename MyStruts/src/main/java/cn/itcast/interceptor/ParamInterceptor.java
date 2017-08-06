package cn.itcast.interceptor;

import cn.itcast.context.ActionContext;
import cn.itcast.invocation.ActionInvocation;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by zhen on 2017-08-06.
 */
public class ParamInterceptor implements Interceptor{
    public void init() {

    }

    public String invoke(ActionInvocation actionInvocation) {

        ActionContext actionContext = actionInvocation.getActionContext();
        Object action = actionContext.getStack().seek();
        Map<String, String[]> requestParams = actionContext.getParam();
        try {
            BeanUtils.populate(action, requestParams);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return actionInvocation.invoke();
    }

    public void destroy() {

    }
}
