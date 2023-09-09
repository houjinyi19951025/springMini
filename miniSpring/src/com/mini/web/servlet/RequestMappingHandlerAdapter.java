package com.mini.web.servlet;

import com.mini.beans.BeanException;
import com.mini.web.WebApplicationContext;
import com.mini.web.WebBindingInitializer;
import com.mini.web.WebDataBinder;
import com.mini.web.WebDataBinderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandleAdapter{

    WebApplicationContext wac;

    WebBindingInitializer webBindingInitializer;


    public RequestMappingHandlerAdapter(WebApplicationContext wac){
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request,response, (HandlerMethod) handler);
    }


    private  void handleInternal(HttpServletRequest request,HttpServletResponse response,HandlerMethod handler){
        try {
            invokeHandlerMethod(request,response,handler);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        WebDataBinderFactory webDataBinderFactory = new WebDataBinderFactory();

        Parameter[] methodParameters = handler.getMethod().getParameters();
        Object[] methodParamObjArray = new Object[methodParameters.length];
        int index = 0;
        for(Parameter methodParameter : methodParameters){
            Object methodParamObj = methodParameter.getType().newInstance();
            WebDataBinder wdb = webDataBinderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            webBindingInitializer.initBinder(wdb);
            wdb.bind(request);
            methodParamObjArray[index++] = methodParamObj;
        }

        Object result = handler.getMethod().invoke(handler.getBean(), methodParamObjArray);

        response.getWriter().append(result.toString());


    }
}
