package com.mini.beans;

import com.mini.beans.factory.config.PropertyValues;

public abstract class AbstractPropertyAccessor extends PropertyEditorRegistrySupport {

    PropertyValues propertyValues;

    public void setPropertyValues(PropertyValues pvs){
        this.propertyValues = pvs;
        for (PropertyValue pv : this.propertyValues.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    public abstract void setPropertyValue(PropertyValue pv) ;
}
