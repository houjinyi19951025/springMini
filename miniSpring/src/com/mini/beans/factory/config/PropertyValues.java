package com.mini.beans.factory.config;

import com.mini.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 22:09
 **/

public class PropertyValues {

    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<PropertyValue>();
    }

    public PropertyValues(Map<String,Object> map) {
        this.propertyValueList = new ArrayList<PropertyValue>(10);
        for (Map.Entry<String,Object> e: map.entrySet()) {
            PropertyValue pv = new PropertyValue(e.getKey(),e.getValue());
            this.propertyValueList.add(pv);
        }
    }
    public  List<PropertyValue> getPropertyValueList(){
        return this.propertyValueList;
    }

    public int size(){

        return this.propertyValueList.size();
    }

    public void  addPropertyValue(PropertyValue pv){
        this.propertyValueList.add(pv);
    }

    public  void  addPropertyValue(String name,Object obj,String type,boolean ref){
        this.propertyValueList.add(new PropertyValue(name,obj,type,ref));
    }

    public boolean isEmpty(){

        return this.propertyValueList.isEmpty();

    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[this.propertyValueList.size()]);

    }
}
