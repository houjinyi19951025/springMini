package com.mini.web.context.support;

import com.mini.beans.BeanException;
import com.mini.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.mini.beans.factory.config.BeanDefinition;
import com.mini.beans.factory.config.BeanPostProcessor;
import com.mini.beans.factory.config.ConfigurableListableBeanFactory;
import com.mini.beans.factory.support.DefaultListableBeanFactory;
import com.mini.context.AbstractApplicationContext;
import com.mini.context.ApplicationEvent;
import com.mini.context.ApplicationListener;
import com.mini.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext {

    private WebApplicationContext parentApplicationContext;
    private  ServletContext servletContext;

    DefaultListableBeanFactory beanFactory;
    public AnnotationConfigWebApplicationContext(String fileName) {
      this(fileName,null);
    }




    public AnnotationConfigWebApplicationContext(String fileName,WebApplicationContext parentApplicationContext){
      this.parentApplicationContext = parentApplicationContext;
      this.servletContext= this.parentApplicationContext.getServletContext();
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        List<String> packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        List<String> controllerNames = scanPackages(packageNames);
        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        this.beanFactory = bf;
        this.beanFactory.setParentBeanFactory(this.parentApplicationContext.getBeanFactory());
        loadBeanDefinitions(controllerNames);
        try {
            refresh();
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadBeanDefinitions(List<String> controllerNames) {

        for(String controller :controllerNames){
            String beanID=controller;
            String beanClassName=controller;
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanFactory.registerBeanDefinition(beanID,beanDefinition);
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();

        for(String packName : packageNames ){
            tempControllerNames.addAll(scanPackage(packName));

        }
        return tempControllerNames;
    }

    private Collection<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());

        for(File file :dir.listFiles()){
            if(file.isDirectory()){
                scanPackage(packageName+"."+file.getName());
            }else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }

        }
        return tempControllerNames;

    }
    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void registerBean(String beanName, Object oj) {

    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
//        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());

        try {
            this.beanFactory.addBeanPostProcessor((BeanPostProcessor) (this.beanFactory.getBean("autowiredAnnotationBeanPostProcessor")));
        } catch (BeanException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return null;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    @Override
    protected void finishRefresh() {

    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void initApplicationEventPublisher() {

    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }

    @Override
    public void addApplicationLister(ApplicationListener applicationListener) {

    }
}
