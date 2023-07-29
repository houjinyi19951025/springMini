package com.mini.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 21:04
 **/

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    private List<String> beanNames =  new ArrayList<>();

    private Map<String,Object> singletons =  new ConcurrentHashMap<>();
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons){
            beanNames.add(beanName);
            singletons.put(beanName,singletonObject);
        }

    }

    @Override
    public Object getSingleton(String beanName) {
        return singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return beanNames.contains(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) beanNames.toArray();
    }

    public void removeSingleton(String beanName){
        synchronized (this.singletons){
            singletons.remove(beanName);
            beanNames.remove(beanName);
        }

    }
}
