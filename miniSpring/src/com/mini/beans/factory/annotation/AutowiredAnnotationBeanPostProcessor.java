package com.mini.beans.factory.annotation;

import com.mini.beans.BeanException;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.config.AutowireCapableBeanFactory;
import com.mini.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        Object result = bean;
        Class<?> clazz = result.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            boolean isAutowired = field.isAnnotationPresent(Autowired.class);
            if(isAutowired){
                String filedName = field.getName();
                Object autowiredObj = this.beanFactory.getBean(filedName);
                try {
                    field.setAccessible(true);
                    field.set(result,autowiredObj);
                    System.out.println("autowire " + filedName + " for bean " + beanName);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return result;



    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

//    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
//        this.beanFactory = beanFactory;
//    }
}
