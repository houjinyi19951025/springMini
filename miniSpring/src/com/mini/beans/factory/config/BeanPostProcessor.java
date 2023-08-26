package com.mini.beans.factory.config;

import com.mini.beans.BeanException;
import com.mini.beans.factory.BeanFactory;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException;

    void setBeanFactory(BeanFactory beanFactory);
}
