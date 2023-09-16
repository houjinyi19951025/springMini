package com.mini.http.converter;

import com.mini.util.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DefaultHttpMessageConverter implements HttpMessageConverter{

    String defaultContentType = "text/json;charset=UTF-8";
    String defaultCharacterEncoding = "UTF-8";

    ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public DefaultHttpMessageConverter() {
    }

    @Override
    public void write(Object obj, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(defaultCharacterEncoding);
        response.setContentType(defaultContentType);
        writeInternal(obj,response);
        response.flushBuffer();

    }

    private void writeInternal(Object obj, HttpServletResponse response) throws IOException {
        String sJsonStr = this.objectMapper.writerValueAsString(obj);
        PrintWriter writer = response.getWriter();
        writer.write(sJsonStr);
    }
}
