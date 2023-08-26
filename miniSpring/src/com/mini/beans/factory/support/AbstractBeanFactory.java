package com.mini.beans.factory.support;

import com.mini.beans.ArgumentValue;
import com.mini.beans.BeanException;
import com.mini.beans.PropertyValue;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.config.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract  class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory,BeanDefinitionRegistry {

    public final Map<String, BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>(256);

    public final List<String> beanDefinitionNames=new ArrayList<>();

    public final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

    public AbstractBeanFactory() {
    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeanException e) {
                e.printStackTrace();
            }
        }
    }

    public Object getBean(String beanName) throws BeanException{
        Object singleton = this.getSingleton(beanName);

        if (singleton == null) {
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                System.out.println("get bean null -------------- " + beanName);
                BeanDefinition bd = beanDefinitionMap.get(beanName);
                singleton=createBean(bd);
                this.registerBean(beanName, singleton);

                //beanpostprocessor
                //step 1 : postProcessBeforeInitialization
                applyBeanPostProcessorsBeforeInitialization(singleton, beanName);

                //step 2 : init-method // 执行初始化方法
                if (bd.getInitMethodName() != null && !bd.getInitMethodName().equals("")) {
                    invokeInitMethod(bd, singleton);
                }

                //step 3 : postProcessAfterInitialization
                applyBeanPostProcessorsAfterInitialization(singleton, beanName);
            }

        }
        if (singleton == null) {
            throw new BeanException("bean is null.");
        }
        return singleton;
    }

    abstract public Object applyBeanPostProcessorsAfterInitialization(Object singleton, String beanName) throws BeanException ;
    abstract  public Object applyBeanPostProcessorsBeforeInitialization(Object singleton, String beanName) throws BeanException;

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object  obj =  doCreateBean(beanDefinition);

        this.earlySingletonObjects.put(beanDefinition.getId(),obj);

        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 属性填充
        populateBean(beanDefinition,clz,obj);

        return obj;

    }
    private void populateBean(BeanDefinition bd, Class<?> clz, Object obj) {
        handleProperties(obj, clz,bd);
    }

    private void handleProperties(Object obj, Class<?> clz, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if(!propertyValues.isEmpty()){
            for(int i =0;i<propertyValues.size();i++){
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String type = propertyValue.getType();
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                boolean ref = propertyValue.getRef();
                Class<?> paramType = null;
                Object pV = null;
                if(!ref){
                    if("String".equals(type) ||"java.lang.String".equals(type)){
                        paramType = String.class;
                    } else if("Integer".equals(type) || "java.lang.Integer".equals(type)){
                        paramType = Integer.class;
                    }else{
                        paramType = String.class;
                    }
                    pV = value;
                }else{
                    try {
                        pV = value;
                        paramType = Class.forName(type);

                        value = getBean((String) pV);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (BeanException e) {
                        throw new RuntimeException(e);
                    }

                }

                String methodName = "set"+name.substring(0,1).toUpperCase() + name.substring(1);

                try {
                    Method method = clz.getMethod(methodName, paramType);
                    try {
                        method.invoke(obj,value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            ConstructorArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            int argumentCount = argumentValues.getArgumentCount();
            if(!argumentValues.isEmpty()){
                Class<?>[] paramTypes = new Class<?>[argumentCount];
                Object[] paramValues = new Object[argumentCount];
                for(int i= 0;i<argumentCount;i++){
                    ConstructorArgumentValue indexArgumentValue = argumentValues.getIndexArgumentValue(i);
                    if("String".equals(indexArgumentValue.getType()) ||"java.lang.String".equals(indexArgumentValue.getType())){
                        paramTypes[i] = String.class;
                        paramValues[i] = indexArgumentValue.getValue();
                    } else if("Integer".equals(indexArgumentValue.getType()) ||"java.lang.Integer".equals(indexArgumentValue.getType())){
                        paramTypes[i] = Integer.class;
                        paramValues[i] = indexArgumentValue.getValue();
                    }else{
                        paramTypes[i] = String.class;
                        paramValues[i] = indexArgumentValue.getValue();
                    }

                }
                obj = clz.getConstructor(paramTypes).newInstance(paramValues);
            }else{
                obj = clz.newInstance();

            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    private void invokeInitMethod(BeanDefinition bd, Object obj) {
        Class<?> clz = obj.getClass();
        Method method = null;
        try {
            method = clz.getMethod(bd.getInitMethodName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);

    }
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name,bd);
        this.beanDefinitionNames.add(name);
//        if (!bd.isLazyInit()) {
//            try {
//                getBean(name);
//            } catch (BeanException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
    }


    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);

    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }




}
