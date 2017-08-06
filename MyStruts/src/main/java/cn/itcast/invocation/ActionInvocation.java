package cn.itcast.invocation;

import cn.itcast.config.ActionConfig;
import cn.itcast.context.ActionContext;
import cn.itcast.interceptor.Interceptor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhen on 2017-08-06.
 */
public class ActionInvocation {
    private static final Logger logger = Logger.getLogger(ActionInvocation.class);
    private Iterator<Interceptor> interceptors;
    private Object action;
    private ActionConfig actionConfig;
    private ActionContext actionContext;
    private String resultUrl;

    public ActionContext getActionContext() {
        return actionContext;
    }


    public ActionInvocation(List<String> classNames, ActionConfig actionConfig, HttpServletRequest request, HttpServletResponse response){
        //装载Interceptor链
        if(classNames != null && classNames.size() > 0){
            List<Interceptor> interceptorList = new ArrayList<Interceptor>();
            for(String className : classNames){
                try {
                    Interceptor interceptor = (Interceptor) Class.forName(className).newInstance();
                    interceptor.init();
                    interceptorList.add(interceptor);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException("创建Interceptor失败，Interceptor Name:" + className ,e);
                }
            }
            interceptors =  interceptorList.iterator();
        }

        //准备action实例
        this.actionConfig = actionConfig;
        try {
            action = Class.forName(actionConfig.getClassName()).newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("创建Action实例失败！" + actionConfig.getClass(), e);
        }

        //准备数据中心
        actionContext = new ActionContext(request, response, action);
    }

    public String invoke(){
       if(interceptors != null && interceptors.hasNext() && resultUrl == null){
           Interceptor interceptor = interceptors.next();
           resultUrl = interceptor.invoke(this);
       }else{
           try{
               Method executeMethod = Class.forName(actionConfig.getClassName()).getMethod(actionConfig.getMethod());
               resultUrl = (String) executeMethod.invoke(action);
           }catch(Exception ex){
               logger.error(ex.getMessage());
               throw new RuntimeException("您配置的action方法不存在" + actionConfig.getClassName());
           }
       }
       return resultUrl;
    }
}
