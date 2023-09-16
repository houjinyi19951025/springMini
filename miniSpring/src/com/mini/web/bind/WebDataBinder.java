package com.mini.web.bind;

import com.mini.beans.AbstractPropertyAccessor;
import com.mini.beans.PropertyEditor;
import com.mini.beans.factory.config.PropertyValues;
import com.mini.util.WebUtils;
import com.mini.web.BeanWrapperImpl;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.sun.corba.se.impl.naming.cosnaming.NamingContextImpl.doBind;

public class WebDataBinder {

    private  Object target;

    private Class<?> clz;

    private String objectName;

    AbstractPropertyAccessor propertyAccessor;

    public WebDataBinder(Object target) {
        this(target,"");
    }

    public WebDataBinder(Object target,  String objectName) {
        this.target = target;
        this.clz =  this.target.getClass();;
        this.objectName = objectName;
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }

    public void bind(HttpServletRequest request){
        PropertyValues propertyValues = assignParameters(request);

        addBindValues(propertyValues,request);

        doBind( propertyValues);

    }

    private void doBind(PropertyValues propertyValues){
        applyPropertyValues(propertyValues);
    }

    private void addBindValues(PropertyValues propertyValues, HttpServletRequest request) {
    }

    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String,Object> map = WebUtils.getParametersStartingWith(request,"");
        return new PropertyValues(map);
    }

    protected void applyPropertyValues(PropertyValues mpvs) {
        getPropertyAccessor().setPropertyValues(mpvs);
    }

    protected AbstractPropertyAccessor getPropertyAccessor() {
        return this.propertyAccessor;
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }
}
