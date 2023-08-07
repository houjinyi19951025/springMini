package com.mini.beans;

/** 
* @Description: bean 的仓库
* @Param: 
* @return: 
* @Author: houjinyi
* @Date: 2023/8/6
*/

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition bd);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}
