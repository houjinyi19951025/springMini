package com.mini.beans.factory.config;

import com.mini.beans.BeanException;
import com.mini.beans.factory.BeanFactory;


public interface BeanFactoryPostProcessor {
	void postProcessBeanFactory(BeanFactory beanFactory) throws BeanException;
}
