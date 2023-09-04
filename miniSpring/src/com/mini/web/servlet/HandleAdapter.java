package com.mini.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandleAdapter {

    void handle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception;
}
