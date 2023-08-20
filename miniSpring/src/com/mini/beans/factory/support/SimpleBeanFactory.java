package com.mini.beans.factory.support;

import com.mini.beans.ArgumentValue;
import com.mini.beans.BeanException;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.PropertyValue;
import com.mini.beans.factory.config.ArgumentValues;
import com.mini.beans.factory.config.BeanDefinition;
import com.mini.beans.factory.config.PropertyValues;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 20:06
 **/

public class SimpleBeanFactory  extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> definitionMap =  new ConcurrentHashMap<>(256);

    private List<String> beanDefinitionNames=new ArrayList<>();

    private final  Map<String,Object> earlySingletonObjects = new HashMap<>(256);


    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.definitionMap.put(name,bd);
        this.beanDefinitionNames.add(name);
//        try {
//            getBean(name);
//        } catch (BeanException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = this.getSingleton(beanName);
        if(singleton == null){
            BeanDefinition beanDefinition = definitionMap.get(beanName);
            singleton = this.earlySingletonObjects.get(beanName);
            if(beanDefinition == null){
                throw new BeanException("not find beanDefinition");
            }
            if(singleton == null){
                singleton = createBean(beanDefinition);
                this.registerBean(beanName,singleton);
            }
            if(singleton == null ){
                throw new BeanException("singleton is null");
            }
        }
        return singleton;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object  obj =  doCreateBean(beanDefinition);

        this.earlySingletonObjects.put(beanDefinition.getId(),obj);

        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        handleProperties(obj,clz,beanDefinition);
        return obj;

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
            ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            int argumentCount = argumentValues.getArgumentCount();
            if(!argumentValues.isEmpty()){
                Class<?>[] paramTypes = new Class<?>[argumentCount];
                Object[] paramValues = new Object[argumentCount];
                for(int i= 0;i<argumentCount;i++){
                    ArgumentValue indexArgumentValue = argumentValues.getIndexArgumentValue(i);
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

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.definitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.definitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.definitionMap.get(name).getClass();
    }

    @Override
    public void registerBean(String beanName,Object obj) {
        this.registerSingleton(beanName,obj);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition){
        this.definitionMap.put(beanDefinition.getId(),beanDefinition);

    }



    @Override
    public void removeBeanDefinition(String name) {
        this.definitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.definitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.definitionMap.containsKey(name);
    }

    public void  refresh(){
        for(String beanName :this.beanDefinitionNames){
            try{
                getBean(beanName);
            }catch (BeanException e){

            }


        }

    }
}
