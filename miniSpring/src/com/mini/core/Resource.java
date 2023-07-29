package com.mini.core;
import java.util.Iterator;

/**
 * @author iyb-houjinyi
 */
public interface Resource extends Iterable<Object> {

    /**
     * fetch data by beanName
     *
     * @return object
     */
    public boolean hasNext();
    /**
     * fetch data by beanName
     *
     * @return object
     */
    public Object next();

}
