package com.mini.beans.factory.support;


import com.mini.beans.BeanException;
import com.mini.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.mini.beans.factory.config.BeanDefinition;
import com.mini.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// IOC 引擎
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
					implements ConfigurableListableBeanFactory {

	ConfigurableListableBeanFactory parentBeanFactory;

	@Override
	public int getBeanDefinitionCount() {
		return this.beanDefinitionMap.size();
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return (String[]) this.beanDefinitionNames.toArray(new String[this.beanDefinitionNames.size()]);
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type) {
		List<String> result = new ArrayList<>();

		for (String beanName : this.beanDefinitionNames) {
			boolean matchFound = false;
			BeanDefinition mbd = this.getBeanDefinition(beanName);
			Class<?> classToMatch = mbd.getClass();
			if (type.isAssignableFrom(classToMatch)) {
				matchFound = true;
			}
			else {
				matchFound = false;
			}

			if (matchFound) {
				result.add(beanName);
			}
		}
		return (String[]) result.toArray();

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
		String[] beanNames = getBeanNamesForType(type);
		Map<String, T> result = new LinkedHashMap<>(beanNames.length);
		for (String beanName : beanNames) {
			Object beanInstance = getBean(beanName);
			result.put(beanName, (T) beanInstance);
		}
		return result;
	}

	public  void setParentBeanFactory(ConfigurableListableBeanFactory beanFactory){
		this.parentBeanFactory = beanFactory;
	}
	@Override
	public void registerDependentBean(String beanName, String dependentBeanName) {

	}

	@Override
	public String[] getDependentBeans(String beanName) {
		return new String[0];
	}

	@Override
	public String[] getDependenciesForBean(String beanName) {
		return new String[0];
	}

	@Override
	public Object getBean(String beanName) throws BeanException {
		Object result =  super.getBean(beanName);
		if(result == null){
			result = this.parentBeanFactory.getBean(beanName);
		}
		return result;
	}
}
