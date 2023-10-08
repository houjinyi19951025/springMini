package com.mini.aop;

public interface MethodInterceptor extends  Interceptor{

    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
