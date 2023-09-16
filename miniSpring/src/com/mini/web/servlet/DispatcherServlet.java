package com.mini.web.servlet;

import com.mini.beans.BeanException;
import com.mini.web.context.support.AnnotationConfigWebApplicationContext;
import com.mini.web.context.WebApplicationContext;
import com.mini.web.context.support.XmlScanComponentHelper;
import com.mini.web.method.HandlerMethod;
import com.mini.web.method.annotation.RequestMappingHandleMapping;
import com.mini.web.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.sun.scenario.effect.impl.prism.PrEffectHelper.render;

public class DispatcherServlet extends HttpServlet {
    private WebApplicationContext webApplicationContext;

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    public static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";
    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";

    public static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";


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

    private ViewResolver viewResolver;


    public DispatcherServlet(){
        super();
    }






    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

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

        ModelAndView mv = ha.handle(processedRequest, resp, handlerMethod);

        render(processedRequest,resp,mv);


    }

    private void render(HttpServletRequest processedRequest, HttpServletResponse resp, ModelAndView mv) throws Exception {
        if(mv == null){
            resp.getWriter().flush();
            resp.getWriter().close();
            return;
        }
        String sTarget =mv.getViewName();
        Map<String, Object> model = mv.getModel();

        View view = resolveViewName(sTarget);

        view.render(model,processedRequest,resp);





    }

    private View resolveViewName(String sTarget) {
        View view = null;
        try {
            if(viewResolver == null){
                return view;
            }
             view = viewResolver.resolveViewName(sTarget);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return view;
    }

    private void Refresh() {
        //初始化controller
//        initController();
        // 处理器映射器
        initHandlerMappings(this.webApplicationContext);

        initHandlerAdapters(this.webApplicationContext);

        initViewResolvers(this.webApplicationContext);

    }

    protected  void initHandlerMappings(WebApplicationContext wac){
        try {
            this.handlerMapping = (HandlerMapping) wac.getBean(HANDLER_MAPPING_BEAN_NAME);
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }

    protected  void initHandlerAdapters(WebApplicationContext wac){
        try {
            this.handleAdapter = (HandleAdapter) wac.getBean(HANDLER_ADAPTER_BEAN_NAME);
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }

    protected void initViewResolvers(WebApplicationContext wac) {
        try {
            this.viewResolver = (ViewResolver) wac.getBean(VIEW_RESOLVER_BEAN_NAME);
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }
//    private void initController() {
//        this.controllerNames = Arrays.asList( this.webApplicationContext.getBeanDefinitionNames());
//        for(String controllerName : this.controllerNames){
//            Class<?> clz = null;
//            Object obj = null;
//            try {
//                clz = Class.forName(controllerName);
//                this.controllerClasses.put(controllerName,clz);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                obj = clz.newInstance();
//                this.controllerObjs.put(controllerName,obj);
//            } catch (InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

}
