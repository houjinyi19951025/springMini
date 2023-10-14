package com.mini.aop;

import com.mini.beans.BeanException;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.BeanFactoryAware;
import com.mini.beans.factory.FactoryBean;


public class ProxyFactoryBean implements FactoryBean<Object>, BeanFactoryAware {

    private Object singletonInstance;

    private Object target;

    private AopProxyFactory aopProxyFactory;

    private BeanFactory beanFactory;

    private String interceptorName;

    private PointcutAdvisor advisor;

    private  synchronized  void initializeAdvisor(){
        Object advice = null;
        MethodInterceptor methodInterceptor = null;

        try {
            advice = this.beanFactory.getBean(this.interceptorName);
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
        this.advisor = (PointcutAdvisor) advice;

//        if(advice instanceof  BeforeAdvice){
//            methodInterceptor = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advice);
//        }else if(advice instanceof AfterAdvice){
//            methodInterceptor = new AfterReturningAdviceInterceptor((AfterReturningAdvice) advice);
//        }else if(advice instanceof  MethodInterceptor){
//            methodInterceptor = (MethodInterceptor) advisor;
//        }
//        advisor = new DefaultAdvisor();
//
//        advisor.setMethodInterceptor(methodInterceptor);


    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    @Override
    public Object getObject() throws Exception {
        initializeAdvisor();
        return  getSingletonInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    private  synchronized Object  getSingletonInstance() {
        if(this.singletonInstance == null){
            this.singletonInstance = getProxy(createAopProxy());
        }
        System.out.println("----------return proxy for :"+this.singletonInstance+"--------"+this.singletonInstance.getClass());
        return this.singletonInstance;

    }

    private Object getProxy(AopProxy aopProxy) {
        return aopProxy.getProxy();
    }

    private AopProxy createAopProxy() {
        System.out.println("----------createAopProxy for :"+target+"--------");

        return getAopProxyFactory().createAopProxy(target,this.advisor);
    }

    private AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
