package com.mini.core;

import com.mini.beans.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @program: miniSpring
 * @description:[module-module]-[flow-flow]-[tag-tag]
 * @author: houjinyi
 * @create: 2023-07-29 19:37
 **/

public class ClassPathXmlResource implements Resource{

    private Document document;

    private Element element;

    private  Iterator<Element> elementIterator;


    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(url);
            Element rootElement = document.getRootElement();
            this.document = document;
            this.element = rootElement;
            this.elementIterator =this.element.elementIterator();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean hasNext(){
        return this.elementIterator.hasNext();
    }
    @Override
    public Object next(){
        return this.elementIterator.next();
    }

    @Override
    public Iterator<Object> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Object> action) {
        Resource.super.forEach(action);
    }

    @Override
    public Spliterator<Object> spliterator() {
        return Resource.super.spliterator();
    }
}
