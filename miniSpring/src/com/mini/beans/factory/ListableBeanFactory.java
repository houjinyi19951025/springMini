package com.mini.beans.factory;


import com.mini.beans.BeanException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

	boolean containsBeanDefinition(String beanName);
    // 获得beanDefinition 数量
	int getBeanDefinitionCount();
	// 获得beanDefinition 名字列表
	String[] getBeanDefinitionNames();
    //通过类型获得bean名称列表
	String[] getBeanNamesForType(Class<?> type);
	// 通过类型获得bean
	<T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;

}

