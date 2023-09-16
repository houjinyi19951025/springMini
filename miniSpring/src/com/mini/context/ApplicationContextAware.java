package com.mini.context;

import com.mini.beans.BeanException;

public interface ApplicationContextAware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}
