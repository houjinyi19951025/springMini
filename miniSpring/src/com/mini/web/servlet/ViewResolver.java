package com.mini.web.servlet;

public interface ViewResolver {
	View resolveViewName(String viewName) throws Exception;
}
