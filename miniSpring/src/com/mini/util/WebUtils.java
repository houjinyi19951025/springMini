package com.mini.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

public class WebUtils {
    public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix) {

        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<>();
        if(prefix == null){
            prefix = "";
        }
        while (parameterNames !=null && parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            if(prefix.isEmpty() || parameterName.startsWith(prefix)){
                String unPrefixed = parameterName.substring(prefix.length());
                String value = request.getParameter(unPrefixed);
                params.put(unPrefixed,value);
            }

        }
        return params;
    }
}
