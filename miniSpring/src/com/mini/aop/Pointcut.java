package com.mini.aop;

public interface Pointcut {
	//ClassFilter getClassFilter();

	MethodMatcher getMethodMatcher();

}
