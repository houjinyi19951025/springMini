package com.mini.beans.factory.support;

import com.mini.beans.BeanException;
import com.mini.beans.factory.FactoryBean;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    protected  Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean){
        return factoryBean.getObjectType();
    }

    protected  Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName){

        Object object = doGetObjectFromFactoryBean(factory,beanName);

        try {
            object = postProcessObjectFromFactoryBean(object, beanName);
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
        return object;

    }

    private Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeanException {

        return object;
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        Object object = null;
        try {
             object = factory.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    protected FactoryBean<?> getFactoryBean(String beanName,Object beanInstance) throws BeanException {

        if(!(beanInstance instanceof FactoryBean)){
            throw new BeanException(
                    "Bean instance of type [" + beanInstance.getClass() + "] is not a FactoryBean");
        }

        return (FactoryBean<?>) beanInstance;

    }


}
