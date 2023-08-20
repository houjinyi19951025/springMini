package com.mini.beans.factory.config;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 20:53
 **/

public interface SingletonBeanRegistry {

    /**
     * Registers a singleton object with the given bean name.
     *
     * @param beanName        the name of the bean to register
     * @param singletonObject the singleton object to register
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * Retrieves the singleton object associated with the given bean name.
     *
     * @param beanName the name of the bean to retrieve
     * @return the singleton object associated with the bean name, or null if not found
     */
    Object getSingleton(String beanName);

    /**
     * Checks if a singleton object is registered with the given bean name.
     *
     * @param beanName the name of the bean to check
     * @return true if a singleton object is registered with the bean name, false otherwise
     */
    boolean containsSingleton(String beanName);

    /**
     * Retrieves the names of all registered singleton objects.
     *
     * @return an array of bean names of registered singleton objects
     */

    String[] getSingletonNames();

}