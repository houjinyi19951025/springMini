package com.mini.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    Object target;

    PointcutAdvisor advisor;

    public JdkDynamicAopProxy(Object target,PointcutAdvisor advisor) {
        this.target = target;

        this.advisor = advisor;
    }

    @Override
    public Object getProxy() {
        System.out.println("----------Proxy new psroxy instance for  ---------"+target);
        System.out.println("----------Proxy new psroxy instance  classloader ---------"+JdkDynamicAopProxy.class.getClassLoader());
        System.out.println("----------Proxy new psroxy instance  interfaces  ---------"+target.getClass().getInterfaces());
        Object obj = Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
        System.out.println("----------Proxy new psroxy instance created r ---------"+obj);
        return obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> targetClass = (target != null ? target.getClass() : null);
        if(this.advisor.getPointcut().getMethodMatcher().matches(method,targetClass)){
            System.out.println("-----before call real object, dynamic proxy........");
            MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
            MethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
            return interceptor.invoke(reflectiveMethodInvocation);
        }
        return null;
    }
}
