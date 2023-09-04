package com.mini.web.servlet;

import com.mini.beans.BeanException;
import com.mini.web.AnnotationConfigWebApplicationContext;
import com.mini.web.RequestMapping;
import com.mini.web.WebApplicationContext;
import com.mini.web.XmlScanComponentHelper;

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
    private WebApplicationContext webApplicationContext;

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    private WebApplicationContext parentApplicationContext;
    private String sContextConfigLocation;

    private List<String> packageNames = new ArrayList<>();

    private Map<String,Object> controllerObjs = new HashMap<>();

    // conto
    private List<String> controllerNames = new ArrayList<>();

    private Map<String,Class<?>> controllerClasses = new HashMap<>();

    private List<String> urlMappingNames = new ArrayList<>();

    private Map<String,Object> mappingObjs = new HashMap<>();

    private Map<String, Method> mappingMethods = new HashMap<>();

    private HandlerMapping handlerMapping;

    private HandleAdapter handleAdapter;


    public DispatcherServlet(){
        super();
    }






    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation,this.parentApplicationContext);

        Refresh();
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE,this.webApplicationContext);
        try {
            doDisPatch(req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doDisPatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpServletRequest processedRequest = req;
        HandlerMethod handlerMethod = null;
        try {
            handlerMethod = this.handlerMapping.getHandler(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(handlerMethod == null){
            return;
        }
        HandleAdapter ha = this.handleAdapter;
        ha.handle(processedRequest,resp,handlerMethod);

    }
    private void Refresh() {
        //初始化controller
        initController();
        // 处理器映射器
        initHandlerMappings(this.webApplicationContext);

        initHandlerAdapters(this.webApplicationContext);

        initViewResolvers(this.webApplicationContext);

    }

    protected  void initHandlerMappings(WebApplicationContext wac){
        this.handlerMapping = new RequestMappingHandleMapping(wac);
    }

    protected  void initHandlerAdapters(WebApplicationContext wac){
        this.handleAdapter = new RequestMappingHandlerAdapter(wac);
    }

    protected void initViewResolvers(WebApplicationContext wac) {

    }
    private void initController() {
        this.controllerNames = Arrays.asList( this.webApplicationContext.getBeanDefinitionNames());
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

}
