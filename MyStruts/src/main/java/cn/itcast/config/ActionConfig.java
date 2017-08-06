package cn.itcast.config;

import java.util.Map;

/**
 * Created by zhen on 2017-08-04.
 */
public class ActionConfig {
    private String name;
    private String method;
    private String className;
    private Map<String, String> results;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getResults() {
        return results;
    }

    public void setResults(Map<String, String> results) {
        this.results = results;
    }
}
