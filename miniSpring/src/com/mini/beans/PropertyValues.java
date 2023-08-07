package com.mini.beans;

import java.util.ArrayList;
import java.util.List;

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

    public  List<PropertyValue> getPropertyValueList(){
        return this.propertyValueList;
    }

    public int size(){

        return this.propertyValueList.size();
    }

    public void  addPropertyValue(PropertyValue pv){
        this.propertyValueList.add(pv);
    }

    public  void  addPropertyValue(String name,Object obj,String type){
        this.propertyValueList.add(new PropertyValue(name,obj,type));
    }

    public boolean isEmpty(){

        return this.propertyValueList.isEmpty();

    }

}
