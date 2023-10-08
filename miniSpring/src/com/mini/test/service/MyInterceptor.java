package com.mini.test.service;


import com.mini.aop.MethodInterceptor;
import com.mini.aop.MethodInvocation;

public class MyInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("method "+invocation.getMethod()+" is called on before "+
				invocation.getThis()+" with args "+invocation.getArguments());
		Object ret=invocation.proceed();
		System.out.println("method "+invocation.getMethod()+" returns after"+ret);
		
		return ret;
	}

}
