package cn.itcast.filter;

import cn.itcast.config.ActionConfig;
import cn.itcast.config.ConfigurationManager;
import cn.itcast.context.ActionContext;
import cn.itcast.invocation.ActionInvocation;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhen on 2017-08-06.
 */
public class StrutsPrepareAndExecuteFilter implements Filter {
    private static final Logger logger = Logger.getLogger(StrutsPrepareAndExecuteFilter.class);
    private List<String> interceptorClassNames;
    private  String extension;
    private Map<String, ActionConfig> actionConfigs;

    public void init(FilterConfig filterConfig) throws ServletException {
        //装载配置信息
        interceptorClassNames = ConfigurationManager.getInterceptors();
        extension = ConfigurationManager.getConstant("struts.action.extension");
        actionConfigs = ConfigurationManager.getActions();
    }

    public static void main(String[] args){
        Logger logger = Logger.getLogger(StrutsPrepareAndExecuteFilter.class);
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //执行
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String urlPath = request.getRequestURI();
        if(!urlPath.endsWith(extension)){
            filterChain.doFilter(request, response);
            return;
        }
        String actionName = urlPath.substring(urlPath.lastIndexOf("/") + 1).replace("." + extension, "");
        ActionConfig actionConfig = actionConfigs.get(actionName);
        if(actionConfig == null){
            throw new RuntimeException("找不到对应的action！" + actionName);
        }
        ActionInvocation actionInvocation = new ActionInvocation(interceptorClassNames, actionConfig, request, response);
        String result = actionInvocation.invoke();
        String dispatcherPath = actionConfig.getResults().get(result);
        if(dispatcherPath == null || "".equals(dispatcherPath)){
            throw new RuntimeException("找不到对应的返回路径！");
        }
        request.getRequestDispatcher(dispatcherPath).forward(request, response);
        ActionContext.tl.remove();
    }

    public void destroy() {

    }
}
