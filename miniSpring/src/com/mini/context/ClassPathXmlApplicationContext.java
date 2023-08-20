package com.mini.context;

import com.mini.beans.*;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.mini.beans.factory.config.AutowireCapableBeanFactory;
import com.mini.beans.factory.support.SimpleBeanFactory;
import com.mini.beans.factory.xml.XmlBeanDefinitionReader;
import com.mini.core.ClassPathXmlResource;

/**
 * @program: miniSpring 上下文
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 16:07
 **/

public class ClassPathXmlApplicationContext implements BeanFactory {
//    SimpleBeanFactory beanFactory;

    AutowireCapableBeanFactory autowireCapableBeanFactory;

    public ClassPathXmlApplicationContext(String fileName,boolean isRefresh) {
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
//        beanFactory = new SimpleBeanFactory();
        AutowireCapableBeanFactory autowireCapableBeanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(autowireCapableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
//        if(isRefresh){
//            beanFactory.refresh();
//        }
        this.autowireCapableBeanFactory = autowireCapableBeanFactory;

        if (isRefresh) {
            try {
                refresh();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (BeanException e) {
                e.printStackTrace();
            }
        }


    }

    public void refresh() throws BeanException, IllegalStateException {
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(this.autowireCapableBeanFactory);

        // Initialize other special beans in specific context subclasses.
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory bf) {
        //if (supportAutowire) {
        bf.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
        //}
    }

    private void onRefresh() {
        this.autowireCapableBeanFactory.refresh();
    }

    @Override
    public Object getBean(String beanId) throws BeanException {

        return autowireCapableBeanFactory.getBean(beanId);

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
        return autowireCapableBeanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return autowireCapableBeanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return autowireCapableBeanFactory.getType(name);
    }

//    @Override
//    public void registerBean(BeanDefinition beanDefinition) {
//        this.beanFactory.registerBeanDefinition(beanDefinition);
//    }


}
