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
     * fetch data by beanName
     *
     * @param beanName
     * @return object
     */
   void registerBean( String beanName,Object oj);

    /**
     * fetch data by beanName
     *
     * @param beanName
     * @return object*/
    Boolean containsBean(String beanName);

}
