package com.mini.beans;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 22:09
 **/

public class PropertyValue {
    private final String name;

    private final  Object value;

    private final String type;

    private final boolean isRef;

    public PropertyValue(String name, Object value,String type,boolean isRef) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.isRef = isRef;
    }

    public String getName() {
        return name;
    }

    public boolean getRef(){
        return this.isRef;
    }
    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
