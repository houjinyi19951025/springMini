package com.mini.test.web.controller;


import com.mini.test.web.entity.User;
import com.mini.web.RequestMapping;

public class HelloWorldBean {
	@RequestMapping("/test1")
	public String doTest1() {
		return "test 1, hello world!";
	}
	@RequestMapping("/test2")
	public String doTest2() {
		return "test 2, hello world!";
	}

	@RequestMapping("/test4")
	public String doTest4(User user) {
		return user.getId() +" "+user.getName() + " " + user.getBirthday();
	}
}
