package com.mini.context;

import com.mini.beans.*;
import com.mini.core.ClassPathXmlResource;
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

public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
    }
    @Override
    public Object getBean(String beanId) throws BeanException {

        return beanFactory.getBean(beanId);

    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }


}
