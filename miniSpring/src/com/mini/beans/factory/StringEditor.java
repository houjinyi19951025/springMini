package com.mini.beans.factory;

import com.mini.beans.PropertyEditor;

public class StringEditor implements PropertyEditor {
    private Class<String> strClass;
    private String strFormat;
    private boolean allowEmpty;
    private Object value;

    public StringEditor(Class<String> strClass,
                        boolean allowEmpty) throws IllegalArgumentException {
        this(strClass, "", allowEmpty);
    }

    public StringEditor(Class<String> strClass,
                        String strFormat, boolean allowEmpty) throws IllegalArgumentException {
        this.strClass = strClass;
        this.strFormat = strFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        setValue(text);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value.toString();
    }

    @Override
    public String getAsText() {
        return this.value.toString();
    }
}
