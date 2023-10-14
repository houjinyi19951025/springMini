package com.mini.aop;

public interface PointcutAdvisor extends Advisor {
	Pointcut getPointcut();
}