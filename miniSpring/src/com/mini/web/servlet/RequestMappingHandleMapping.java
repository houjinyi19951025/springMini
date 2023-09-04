package com.mini.web.servlet;

import com.mini.beans.BeanException;
import com.mini.web.RequestMapping;
import com.mini.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class RequestMappingHandleMapping implements HandlerMapping{
    WebApplicationContext wac;
    private final MappingRegistry mappingRegistry = new MappingRegistry();
    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String sPath = request.getServletPath();
        if(!this.mappingRegistry.getUrlMappingNames().contains(sPath)){
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(sPath);

        Object obj = this.mappingRegistry.getMappingObjs().get(sPath);

        HandlerMethod handlerMethod = new HandlerMethod(method, obj);

        return handlerMethod;

    }

    public RequestMappingHandleMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }

    private void initMapping() {

        Class<?> clz = null;

        Object obj = null;

        String[] controllerNames = this.wac.getBeanDefinitionNames();

        for(String controllerName :controllerNames){
            try {
                clz = Class.forName(controllerName);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                obj = this.wac.getBean(controllerName);
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
                }
            }
        }


    }
}
