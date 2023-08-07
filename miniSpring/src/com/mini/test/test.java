package com.mini.test;

import com.mini.beans.BeanException;
import com.mini.context.ClassPathXmlApplicationContext;

public class test {

    public static void main(String[] args) throws BeanException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("resources/spring.xml");
        TestServiceImpl testServiceImpl = (TestServiceImpl) classPathXmlApplicationContext.getBean("testServiceImpl");
        testServiceImpl.say();
    }
}
