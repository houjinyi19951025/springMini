package com.mini.aop;

public class DefaultAopProxyFactory implements  AopProxyFactory{
    @Override
    public AopProxy createAopProxy(Object target,PointcutAdvisor advisor) {
        return new JdkDynamicAopProxy(target,advisor);
    }
}
