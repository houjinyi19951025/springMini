package com.mini.test.web.controller;


import com.mini.beans.factory.annotation.Autowired;
import com.mini.test.PersonServiceImpl;
import com.mini.test.service.StudentServiceImpl;
import com.mini.test.web.entity.User;
import com.mini.web.bind.annotation.RequestMapping;
import com.mini.web.bind.annotation.ResponseBody;
import com.mini.web.servlet.ModelAndView;

public class HelloWorldBean {

	@Autowired
	PersonServiceImpl personService;

	@Autowired
	StudentServiceImpl studentService;
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

	@RequestMapping("/test5")
	@ResponseBody
	public User doTest7(User user) {
		System.out.println(user.getBirthday());
		user.setName(user.getName() + "---");
		//user.setBirthday(new Date());
		System.out.println(".....................");
		personService.query();
		studentService.query();
		studentService.select();
		System.out.println(".....................");
		return user;
	}

	@RequestMapping("/test6")
	public ModelAndView doTest6(User user) {
		ModelAndView mav = new ModelAndView("test","msg",user.getName());
		return mav;
	}
}
