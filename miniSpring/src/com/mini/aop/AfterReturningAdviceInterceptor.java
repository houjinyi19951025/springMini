package com.mini.aop;

public class AfterReturningAdviceInterceptor implements MethodInterceptor,AfterAdvice{

    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object returnVal = methodInvocation.proceed();
        this.advice.afterReturning(returnVal,methodInvocation.getMethod(),methodInvocation.getArguments(),methodInvocation.getThis());
        return returnVal;
    }
}
