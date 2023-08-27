package com.mini.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DispatcherServlet extends HttpServlet {

    private String sContextConfigLocation;

    private List<String> packageNames = new ArrayList<>();

    private Map<String,Object> controllerObjs = new HashMap<>();

    // conto
    private List<String> controllerNames = new ArrayList<>();

    private Map<String,Class<?>> controllerClasses = new HashMap<>();

    private List<String> urlMappingNames = new ArrayList<>();

    private Map<String,Object> mappingObjs = new HashMap<>();

    private Map<String, Method> mappingMethods = new HashMap<>();


    public DispatcherServlet(){
        super();
    }






    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        Refresh();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sPath = req.getServletPath();
        System.out.println("doGet................sPath"+sPath);
        if(!this.urlMappingNames.contains(sPath)){
            return;
        }
        Method method = this.mappingMethods.get(sPath);
        Object obj = this.mappingObjs.get(sPath);
        Object result = null;
        try {
            result = method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().append(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void Refresh() {
        //初始化controller
        initController();
        //初始化url映射
        initMapping();
    }

    private void initMapping() {
        for(String controllerName : this.controllerNames){
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();
            if(methods != null){
                for(Method method : methods){
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if(isRequestMapping){
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        this.urlMappingNames.add(urlMapping);
                        this.mappingMethods.put(urlMapping,method);
                        this.mappingObjs.put(urlMapping,obj);
                    }
                }

            }

        }
    }

    private void initController() {
        this.controllerNames = scanPackages(this.packageNames);

        for(String controllerName : this.controllerNames){
            Class<?> clz = null;
            Object obj = null;
            try {
                clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName,clz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                obj = clz.newInstance();
                this.controllerObjs.put(controllerName,obj);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
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
}
