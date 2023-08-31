package com.mini.web;

import com.mini.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

public class AnnotationConfigWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private  ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String fileName) {
        super(fileName);
    }

    public AnnotationConfigWebApplicationContext(String fileName, boolean isRefresh) {
        super(fileName, isRefresh);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
