package com.mini.aop;

import java.lang.reflect.Method;

/**
 * 接口的匹配算法
 */
public interface MethodMatcher {
	boolean matches(Method method, Class<?> targetClass);
}
