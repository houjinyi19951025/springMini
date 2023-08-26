package com.mini.context;

import com.mini.beans.BeanException;
import com.mini.beans.factory.ListableBeanFactory;
import com.mini.beans.factory.config.BeanFactoryPostProcessor;
import com.mini.beans.factory.config.ConfigurableBeanFactory;
import com.mini.beans.factory.config.ConfigurableListableBeanFactory;
import com.mini.core.env.Environment;
import com.mini.core.env.EnvironmentCapable;

public interface ApplicationContext
		extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher{
	String getApplicationName();

	long getStartupDate();

	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

	void setEnvironment(Environment environment);

	Environment getEnvironment();

	void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
	void refresh() throws BeanException, IllegalStateException;

	void close();

	boolean isActive();

}
