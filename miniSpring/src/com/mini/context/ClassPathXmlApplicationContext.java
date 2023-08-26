package com.mini.context;

import com.mini.beans.*;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.mini.beans.factory.config.AutowireCapableBeanFactory;
import com.mini.beans.factory.config.BeanFactoryPostProcessor;
import com.mini.beans.factory.config.BeanPostProcessor;
import com.mini.beans.factory.config.ConfigurableListableBeanFactory;
import com.mini.beans.factory.support.DefaultListableBeanFactory;
//import com.mini.beans.factory.support.SimpleBeanFactory;
import com.mini.beans.factory.xml.XmlBeanDefinitionReader;
import com.mini.core.ClassPathXmlResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: miniSpring 上下文
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 16:07
 **/

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
//    SimpleBeanFactory beanFactory;

    DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors =
            new ArrayList<BeanFactoryPostProcessor>();

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
        this.beanFactory = defaultListableBeanFactory;

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


    @Override
    void finishRefresh() {
      publishEvent(new ContextRefreshEvent("容器启动完成......"));
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationLister(listener);
    }

    @Override
    void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override


    void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public Object getBean(String beanId) throws BeanException {

        return beanFactory.getBean(beanId);

    }

    @Override
    public void registerBean(String beanName, Object oj) {

    }



    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    @Override
    public void publishEvent(ApplicationEvent event) {
         this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationLister(ApplicationListener applicationListener) {

    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return null;
    }

//    @Override
//    public void registerBean(BeanDefinition beanDefinition) {
//        this.beanFactory.registerBeanDefinition(beanDefinition);
//    }


}
