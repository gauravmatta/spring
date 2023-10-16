package com.springimplant.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {
	
	@RequestMapping("/one")
	public String one() {
		System.out.println("This is one Handler");
		return "redirect:/enjoy";
	}
	
	@RequestMapping("/two")
	public RedirectView two() {
		System.out.println("This is two Handler");
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("enjoy");
		return redirectView;
	}
	
	@RequestMapping("/google")
	public RedirectView google() {
		System.out.println("This is google Handler");
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("https://www.google.com");
		return redirectView;
	}
	
	@RequestMapping("/enjoy")
	public String enjoy() {
		System.out.println("This is enjoy Handler");
		return "contact";
	}
}
