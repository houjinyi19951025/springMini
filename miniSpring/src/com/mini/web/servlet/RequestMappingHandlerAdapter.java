package com.mini.web.servlet;

import com.mini.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestMappingHandlerAdapter implements HandleAdapter{

    WebApplicationContext wac;


    public RequestMappingHandlerAdapter(WebApplicationContext wac){
        this.wac = wac;
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request,response, (HandlerMethod) handler);
    }


    private  void handleInternal(HttpServletRequest request,HttpServletResponse response,HandlerMethod handler){

        Method method = handler.getMethod();
        Object obj = handler.getBean();
        Object res = null;
        try {
            res = method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        try {
            response.getWriter().append(res.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
