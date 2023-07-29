package com.mini.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 20:06
 **/

public class SimpleBeanFactory implements BeanFactory {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String,Object> singletons =  new HashMap<>();

    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = singletons.get(beanName);
        if(singleton == null){
            int i = beanNames.indexOf(beanName);
            if(i == -1){
                throw new BeanException("not find beanName......");
            }
            BeanDefinition beanDefinition = beanDefinitions.get(i);
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            singletons.put(beanName,singleton);

        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanDefinitions.add(beanDefinition);
        beanNames.add(beanDefinition.getId());

    }
}
