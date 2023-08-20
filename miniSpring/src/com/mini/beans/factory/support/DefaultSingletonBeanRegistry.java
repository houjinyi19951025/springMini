package com.mini.beans.factory.support;

import com.mini.beans.factory.config.SingletonBeanRegistry;

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

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private List<String> beanNames =  new ArrayList<>();

    private Map<String,Object> singletonObjects =  new ConcurrentHashMap<>();
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects){
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            beanNames.add(beanName);
            singletonObjects.put(beanName,singletonObject);
        }

    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
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
        synchronized (this.singletonObjects){
            singletonObjects.remove(beanName);
            beanNames.remove(beanName);
        }

    }
}
