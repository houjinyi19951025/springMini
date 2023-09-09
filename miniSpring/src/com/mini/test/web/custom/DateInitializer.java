package com.mini.test.web.custom;


import com.mini.web.WebBindingInitializer;
import com.mini.web.WebDataBinder;

import java.util.Date;

public class DateInitializer implements WebBindingInitializer {
	@Override
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class,"yyyy-MM-dd", false));
	}
}
