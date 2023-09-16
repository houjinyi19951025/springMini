package com.mini.web.method.annotation;

import com.mini.beans.BeanException;
import com.mini.context.ApplicationContext;
import com.mini.context.ApplicationContextAware;
import com.mini.web.bind.annotation.RequestMapping;
import com.mini.web.context.WebApplicationContext;
import com.mini.web.servlet.HandlerMapping;
import com.mini.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class RequestMappingHandleMapping implements HandlerMapping, ApplicationContextAware {
//    WebApplicationContext wac;

    ApplicationContext applicationContext;
    private  MappingRegistry mappingRegistry =null;
    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        if(this.mappingRegistry == null ){
            this.mappingRegistry = new MappingRegistry();
            initMapping();
        }
        String sPath = request.getServletPath();
        if(!this.mappingRegistry.getUrlMappingNames().contains(sPath)){
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(sPath);

        Object obj = this.mappingRegistry.getMappingObjs().get(sPath);

        HandlerMethod handlerMethod = new HandlerMethod(method, obj);

        return handlerMethod;

    }

    public RequestMappingHandleMapping() {

    }

//    public RequestMappingHandleMapping(WebApplicationContext wac) {
//        this.wac = wac;
//
//        initMapping();
//    }

    private void initMapping() {

        Class<?> clz = null;

        Object obj = null;

        String[] controllerNames = this.applicationContext.getBeanDefinitionNames();

        for(String controllerName :controllerNames){
            try {
                clz = Class.forName(controllerName);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                obj = this.applicationContext.getBean(controllerName);
            } catch (BeanException e) {
                throw new RuntimeException(e);
            }
        }

        Method[] methods = clz.getDeclaredMethods();

        if(methods !=null){
            for(Method method :methods){
                boolean isRequestMapping =  method.isAnnotationPresent(RequestMapping.class);
                if(isRequestMapping){
                    String methodName = method.getName();
                    String urlMapping  = method.getAnnotation(RequestMapping.class).value();
                    this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                    this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                    this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                    this.mappingRegistry.getMappingMethodNames().put(urlMapping, methodName);
                    this.mappingRegistry.getMappingClasses().put(urlMapping, clz);
                }
            }
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = applicationContext;
    }
}
