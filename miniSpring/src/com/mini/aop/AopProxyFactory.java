package com.mini.aop;

public interface AopProxyFactory {
    AopProxy createAopProxy(Object target);
}
