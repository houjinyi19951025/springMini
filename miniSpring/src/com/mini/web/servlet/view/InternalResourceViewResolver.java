package com.mini.web.servlet.view;

import com.mini.web.servlet.View;
import com.mini.web.servlet.ViewResolver;

public class InternalResourceViewResolver implements ViewResolver {

    private Class<?> viewClass = null;

    private String viewClassName = "";

    private String prefix = "";

    private String suffix = "";

    private String contentType;


    public InternalResourceViewResolver(){
        if(getViewClass() == null){
            setViewClass(JstlView.class);
        }
    }

    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }
    protected Class<?> getViewClass() {
        return this.viewClass;
    }

    public String getViewClassName() {
        return viewClassName;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public View resolveViewName(String viewName) throws Exception {
      return buildView(viewName);
    }

    public View buildView(String viewName) throws InstantiationException, IllegalAccessException {
        Class<?> viewClass = getViewClass();
        View view = (View)viewClass.newInstance();
        view.setUrl(getPrefix()+viewName+getSuffix());
        view.setContentType(getContentType());
        return view;

    }
}
