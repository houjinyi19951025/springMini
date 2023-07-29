package com.mini.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 20:06
 **/

public class SimpleBeanFactory  extends DefaultSingletonBeanRegistry implements BeanFactory{

    private Map<String,BeanDefinition> definitionMap =  new ConcurrentHashMap<>(256);

    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = this.getSingleton(beanName);
        if(singleton == null){
            BeanDefinition beanDefinition = definitionMap.get(beanName);
            if(beanDefinition == null){
                throw new BeanException("not find beanDefinition");
            }
            try {
                 singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.registerBean(beanName,singleton);
        }
        return singleton;
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName,Object obj) {
        this.registerSingleton(beanName,obj);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition){
        this.definitionMap.put(beanDefinition.getId(),beanDefinition);

    }



}
