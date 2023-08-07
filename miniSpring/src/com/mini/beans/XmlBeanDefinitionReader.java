package com.mini.beans;

import com.mini.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 19:53
 **/

public class XmlBeanDefinitionReader {
    private  SimpleBeanFactory beanFactory;
    public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while(resource.hasNext()){
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            List<Element> propertyList = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            for(Element e : propertyList){
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                propertyValues.addPropertyValue(name,value,type);
            }
            beanDefinition.setPropertyValues(propertyValues);
            List<Element> constructorList = element.elements("constructor-arg");
            ArgumentValues argumentValues = new ArgumentValues();
            for(Element e :constructorList){
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                argumentValues.addGenericArgumentValue(new ArgumentValue(value,type,name));
            }
            beanDefinition.setConstructorArgumentValues(argumentValues);


            beanFactory.registerBeanDefinition(beanDefinition.getId(),beanDefinition);

        }

    }
}
