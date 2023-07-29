package com.mini.context;

import com.mini.beans.BeanDefinition;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.*;

/**
 * @program: miniSpring 上下文
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 16:07
 **/

public class ClassPathXmlApplicationContext {
   private List<BeanDefinition> beanDefinitions = new ArrayList<>();
   private Map<String, Object> singleton =  new HashMap<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();

    }

    private  void readXml(String fileName){
        SAXReader saxReader = new SAXReader();
        try {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(url);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            for(Element element : elements ){
                String beanId = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
                beanDefinitions.add(beanDefinition);
            }

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
    private void instanceBeans(){

        for(BeanDefinition beanDefinition :beanDefinitions){
            try {
                Object bean = Class.forName(beanDefinition.getClassName()).newInstance();
                singleton.put(beanDefinition.getId(),bean);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public Object getBean(String beanId){
        return singleton.get(beanId);

    }


}
