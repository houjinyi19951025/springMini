package com.mini.aop;

import com.mini.beans.factory.FactoryBean;


public class ProxyFactoryBean implements FactoryBean<Object> {

    private Object singletonInstance;

    private Object target;

    private AopProxyFactory aopProxyFactory;

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

        return getAopProxyFactory().createAopProxy(target);
    }

    private AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
