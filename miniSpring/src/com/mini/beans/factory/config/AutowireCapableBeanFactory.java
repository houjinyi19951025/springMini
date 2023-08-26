package com.mini.beans.factory.config;


import com.mini.beans.BeanException;
import com.mini.beans.factory.BeanFactory;


public interface AutowireCapableBeanFactory extends BeanFactory {

    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeanException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeanException;

//    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<AutowiredAnnotationBeanPostProcessor>();
//
//    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
//        this.beanPostProcessors.remove(beanPostProcessor);
//        this.beanPostProcessors.add(beanPostProcessor);
//    }
//
//    public int getBeanPostProcessorCount() {
//        return this.beanPostProcessors.size();
//    }
//
//    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
//        return this.beanPostProcessors;
//    }
//
//    @Override
//    public Object applyBeanPostProcessorsAfterInitialization(Object singleton, String beanName) throws BeanException {
//        Object result = singleton;
//        for (AutowiredAnnotationBeanPostProcessor beanProcessor : getBeanPostProcessors()) {
//            beanProcessor.setBeanFactory(this);
//            result = beanProcessor.postProcessBeforeInitialization(result, beanName);
//            if (result == null) {
//                return result;
//            }
//        }
//
//        return result;
//    }
//
//    @Override
//    public Object applyBeanPostProcessorsBeforeInitialization(Object singleton, String beanName) throws BeanException {
//        Object result = singleton;
//        for (AutowiredAnnotationBeanPostProcessor beanProcessor : getBeanPostProcessors()) {
//            beanProcessor.setBeanFactory(this);
//            result = beanProcessor.postProcessAfterInitialization(result, beanName);
//            if (result == null) {
//                return result;
//            }
//        }
//
//        return result;
//    }

//    @Override
//    public void registerBeanDefinition(String name, BeanDefinition bd) {
//
//
//    }
}
