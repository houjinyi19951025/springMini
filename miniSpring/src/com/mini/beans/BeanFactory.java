package com.mini.beans;

/**
 * @author iyb-houjinyi
 */
public interface BeanFactory {
    /**
     * fetch data by beanName
     *
     * @param beanName
     * @return object
     */
    Object getBean(String beanName) throws BeanException;

    /**
     * fetch data by beanDefinition
     *
     * @param beanDefinition
     * @return object
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
