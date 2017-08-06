package cn.itcast.context;


import cn.itcast.constant.Constant;
import cn.itcast.stack.ValueStack;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhen on 2017-08-06.
 */
public class ActionContext {

    public static ThreadLocal<ActionContext> tl = new ThreadLocal<ActionContext>();
    private Map<String,Object> context = new HashMap();

    public ActionContext(HttpServletRequest request, HttpServletResponse response, Object action){
        //ActionContext中存放6中东西，分别为request、response、session、param、application、valueStack
        context.put(Constant.REQUEST, request);
        context.put(Constant.RESPONSE, response);
        context.put(Constant.SESSION, request.getSession());
        context.put(Constant.PARAM, request.getParameterMap());
        context.put(Constant.APPLICATION, request.getSession().getServletContext());
        ValueStack stackValue = new ValueStack();
        stackValue.push(action);
        request.setAttribute(Constant.VALUE_STACK, stackValue);
        context.put(Constant.VALUE_STACK, stackValue);
        tl.set(this);
    }

    public HttpServletRequest getRequest(){
        return (HttpServletRequest) context.get(Constant.REQUEST);
    }

    public HttpServletResponse getResponse(){
        return (HttpServletResponse) context.get(Constant.RESPONSE);
    }
    public HttpSession getSession(){
        return (HttpSession) context.get(Constant.SESSION);
    }
    public ServletContext getApplication(){
        return (ServletContext) context.get(Constant.APPLICATION);
    }
    public Map<String, String[]> getParam(){
        return (Map<String, String[]>) context.get(Constant.PARAM);
    }
    public ValueStack getStack(){
        return (ValueStack) context.get(Constant.VALUE_STACK);
    }

}
