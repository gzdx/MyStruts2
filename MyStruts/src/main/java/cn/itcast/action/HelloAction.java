package cn.itcast.action;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhen on 2017-08-04.
 */
public class HelloAction {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static Logger logger = Logger.getLogger(HelloAction.class);

    public String execute(){
        System.out.println("welcome Hello Action, name = " + name);
        return "success";
    }
}
