package cn.itcast.config;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhen on 2017-08-04.
 * 读取struts.xml配置信息
 */
public class ConfigurationManager {
    private static final Logger logger = Logger.getLogger(ConfigurationManager.class);

    //读取Interceptor
    public static List<String> getInterceptors(){
        List<String> interceptors = null;
        SAXReader saxReader = new SAXReader();
        InputStream inputStream = ConfigurationManager.class.getResourceAsStream("/struts.xml");
        Document document = null;
        try {
            document = saxReader.read(inputStream);
        } catch (DocumentException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("配置文件解析异常" ,e);
        }
        String xpath = "//interceptor";
        List<Element> list = document.selectNodes(xpath);
        if(list != null && list.size() > 0){
            interceptors = new ArrayList<String>();
            for(Element ele: list){
                String className = ele.attributeValue("class");
                interceptors.add(className);
            }
        }
        return interceptors;
    }

    //读取Constant
    public static String getConstant(String name){
        String value = null;
        SAXReader saxReader = new SAXReader();
        InputStream is = ConfigurationManager.class.getResourceAsStream("/struts.xml");
        Document document = null;
        try {
            document = saxReader.read(is);
        } catch (DocumentException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("配置文件解析异常" ,e);
        }
        String xPath = "//constant[@name='" + name + "']";
        List<Element> ele = document.selectNodes(xPath);
        if(ele != null && ele.size() > 0){
            value = ele.get(0).attributeValue("value");
        }
        return value;
    }

    //读取Action
    public static Map<String, ActionConfig> getActions(){
        Map<String, ActionConfig> actions = null;
        SAXReader saxReader = new SAXReader();
        InputStream is = ConfigurationManager.class.getResourceAsStream("/struts.xml");
        Document document = null;
        try {
            document = saxReader.read(is);
        } catch (DocumentException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("配置文件解析异常" ,e);
        }
        String xPath = "//action";
        List<Element> list = document.selectNodes(xPath);
        if(list != null && list.size() > 0){
            actions = new HashMap<String, ActionConfig>();
            for(Element element : list){
                ActionConfig actionConfig = new ActionConfig();
                String name = element.attributeValue("name");
                String method = element.attributeValue("method");
                String className = element.attributeValue("class");
                Map<String, String> results = null;
                List<Element> resultElements = element.elements("result");
                if(resultElements != null && resultElements.size() > 0){
                    results = new HashMap();
                    for(Element ele: resultElements){
                        String resultName = ele.attributeValue("name");
                        String resultValue = ele.getTextTrim();
                        results.put(resultName, resultValue);
                    }
                }
                actionConfig.setName(name);
                actionConfig.setMethod(method == null || method.trim().equals("") ? "execute" : method.trim());
                actionConfig.setClassName(className);
                actionConfig.setResults(results);
                actions.put(name, actionConfig);
            }
        }
        return actions;
    }




}
