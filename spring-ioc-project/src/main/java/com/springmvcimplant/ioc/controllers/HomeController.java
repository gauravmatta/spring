package com.springmvcimplant.ioc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	@ResponseBody
	public String home(Model model)
	{
		return "Welcome Home";
	}
	
	@RequestMapping("/home")
	public String myhome()
	{
		return "home";
	}

}
