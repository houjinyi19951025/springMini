package com.mini.web.method.annotation;

import com.mini.beans.BeanException;
import com.mini.context.ApplicationContext;
import com.mini.context.ApplicationContextAware;
import com.mini.http.converter.HttpMessageConverter;
import com.mini.web.bind.annotation.ResponseBody;
import com.mini.web.context.WebApplicationContext;
import com.mini.web.bind.support.WebBindingInitializer;
import com.mini.web.bind.WebDataBinder;
import com.mini.web.bind.support.WebDataBinderFactory;
import com.mini.web.servlet.HandleAdapter;
import com.mini.web.method.HandlerMethod;
import com.mini.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandleAdapter, ApplicationContextAware {

    private ApplicationContext applicationContext= null;

    WebBindingInitializer webBindingInitializer;

    private HttpMessageConverter messageConverter = null;

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }



    public RequestMappingHandlerAdapter(){
    }
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       return  handleInternal(request,response, (HandlerMethod) handler);
    }


    private  ModelAndView handleInternal(HttpServletRequest request,HttpServletResponse response,HandlerMethod handler){
        try {
           return  invokeHandlerMethod(request,response,handler);
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

    private ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        WebDataBinderFactory webDataBinderFactory = new WebDataBinderFactory();

        Parameter[] methodParameters = handler.getMethod().getParameters();
        Object[] methodParamObjArray = new Object[methodParameters.length];
        int index = 0;
        for(Parameter methodParameter : methodParameters){
            if(methodParameter.getType() !=HttpServletRequest.class && methodParameter.getType() !=HttpServletResponse.class){
                Object methodParamObj = methodParameter.getType().newInstance();
                WebDataBinder wdb = webDataBinderFactory.createBinder(request, methodParamObj, methodParameter.getName());
                webBindingInitializer.initBinder(wdb);
                wdb.bind(request);
                methodParamObjArray[index++] = methodParamObj;
            }else if(methodParameter.getType() == HttpServletRequest.class){
                methodParamObjArray[index++] = request;
            }else if(methodParameter.getType() ==  HttpServletResponse.class){
                methodParamObjArray[index++] = response;
            }
        }
        Method invocableMethod = handler.getMethod();

        Object result = invocableMethod.invoke(handler.getBean(), methodParamObjArray);

        Class<?> returnType = invocableMethod.getReturnType();

        ModelAndView mav = null;

        if(invocableMethod.isAnnotationPresent(ResponseBody.class)){
            this.messageConverter.write(result,response);
        }else if(result instanceof  String){
            String sTarget = (String) result;
            mav = new ModelAndView();
            mav.setView(sTarget);

        }else if(result instanceof ModelAndView){
            mav = (ModelAndView) result;
        }else{

        }
        return mav;




    }
    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = applicationContext;
    }
}
