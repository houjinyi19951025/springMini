package com.mini.aop;

import com.mini.util.PatternMatchUtils;

import java.lang.reflect.Method;

public class NameMatchMethodPointcut implements MethodMatcher,Pointcut {

    private String mappedName = "";

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if(mappedName.equals(method.getName()) || isMatch(method.getName(),mappedName)){
            return true;
        }
        return false;
    }

    private boolean isMatch(String name, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName,name);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
