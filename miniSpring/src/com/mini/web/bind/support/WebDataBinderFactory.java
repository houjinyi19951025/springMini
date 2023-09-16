package com.mini.web.bind.support;

import com.mini.web.bind.WebDataBinder;

import javax.servlet.http.HttpServletRequest;

public class WebDataBinderFactory {

    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName){
        WebDataBinder webDataBinder = new WebDataBinder(target, objectName);
        initBinder(webDataBinder,request);
        return webDataBinder;
    }

    protected void initBinder(WebDataBinder dataBinder, HttpServletRequest request){
    }
}
