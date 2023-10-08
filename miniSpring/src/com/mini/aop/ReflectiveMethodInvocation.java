package com.mini.aop;

import java.lang.reflect.Method;

public class ReflectiveMethodInvocation implements MethodInvocation{

    protected  final  Object proxy;

    protected  final Object target;

    protected final Method method;

    protected  Object[] arguments;

    private Class<?> targetClass;

    protected ReflectiveMethodInvocation(
            Object proxy,  Object target, Method method,  Object[] arguments,
            Class<?> targetClass) {

        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        //this.methodInterceptor = methodInterceptor;
    }
    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(this.target,this.arguments);
    }
}
