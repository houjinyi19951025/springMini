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
            List<String> refs = new ArrayList<>();
            PropertyValues propertyValues = new PropertyValues();
            for(Element e : propertyList){
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                String ref = e.attributeValue("ref");
                boolean isRef = false;
                String pV ="";
                if(ref !=null && !ref.equals("")){
                    isRef= true;
                    pV = ref;
                    refs.add(ref);
                }else{
                    pV = value;
                }
                propertyValues.addPropertyValue(name,pV,type,isRef);
            }
            beanDefinition.setPropertyValues(propertyValues);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
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
