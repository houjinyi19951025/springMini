package com.mini.context;

import com.mini.beans.*;
import com.mini.core.ClassPathXmlResource;

/**
 * @program: miniSpring 上下文
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 16:07
 **/

public class ClassPathXmlApplicationContext implements BeanFactory {
    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
    }
    @Override
    public Object getBean(String beanId) throws BeanException {

        return beanFactory.getBean(beanId);

    }

    @Override
    public void registerBean(String beanName, Object oj) {

    }

    @Override
    public Boolean containsBean(String beanName) {
        return null;
    }

    @Override
    public boolean isSingleton(String name) {
        return beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return beanFactory.getType(name);
    }

//    @Override
//    public void registerBean(BeanDefinition beanDefinition) {
//        this.beanFactory.registerBeanDefinition(beanDefinition);
//    }


}
