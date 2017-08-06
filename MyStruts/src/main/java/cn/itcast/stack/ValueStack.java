package cn.itcast.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhen on 2017-08-06.
 */
public class ValueStack {
    private List<Object> list = new ArrayList();

    public Object pop(){
        return list.remove(0);
    }

    public void push(Object object){
        list.add(0, object);
    }

    public Object seek(){
        return list.get(0);
    }
}
