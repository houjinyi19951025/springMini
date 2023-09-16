package com.mini.web.servlet;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private Object view;

    private Map<String,Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String test, String msg, String name) {
        this.view = test;
        addObject(msg,name);

    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        addAttribute(attributeName, attributeValue);
        return this;
    }

    public void addAttribute(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
    }


    public Object getView() {
        return view;
    }

    public String getViewName() {
        return (this.view instanceof String ? (String) this.view : null);
    }


    public Map<String, Object> getModel() {
        return model;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView(String viewName, Map<String, ?> modelData) {
        this.view = viewName;
        if (modelData != null) {
            addAllAttributes(modelData);
        }
    }

    private void addAllAttributes(Map<String,?> modelData) {
        model.putAll(modelData);
    }
}
