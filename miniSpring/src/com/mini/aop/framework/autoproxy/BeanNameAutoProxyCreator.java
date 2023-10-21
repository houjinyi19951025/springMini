package com.mini.aop.framework.autoproxy;

import com.mini.aop.AopProxyFactory;
import com.mini.aop.DefaultAopProxyFactory;
import com.mini.aop.PointcutAdvisor;
import com.mini.aop.ProxyFactoryBean;
import com.mini.beans.BeanException;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.config.BeanPostProcessor;
import com.mini.util.PatternMatchUtils;

public class BeanNameAutoProxyCreator implements BeanPostProcessor {

    String pattern;

    private  BeanFactory beanFactory;

    private AopProxyFactory aopProxyFactory;

    private String interceptorName;

    private PointcutAdvisor advisor;

    public BeanNameAutoProxyCreator() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }



    public void setAdvisor(PointcutAdvisor advisor) {
        this.advisor = advisor;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        System.out.println(".......................开始创建代理对象");
        if(isMatch(beanName,this.pattern)){
            System.out.println(beanName + "bean name matched, " + this.pattern + " create proxy for " + bean);
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(bean);
            proxyFactoryBean.setBeanFactory(beanFactory);
            proxyFactoryBean.setAopProxyFactory(aopProxyFactory);
            proxyFactoryBean.setInterceptorName(interceptorName);
            bean = proxyFactoryBean;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected boolean isMatch(String beanName, String mappedName) {
        System.out.println(" match?" + beanName + ":" +mappedName);
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }
}
