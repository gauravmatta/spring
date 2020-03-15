package com.springimplant.mvc.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class GlobalControllerAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);
	
	@ModelAttribute("currentDate")
	public Date getCurrentDate()
	{
		return new Date();
	}
	
	@ExceptionHandler(Exception.class)
	public String handleError(HttpServletRequest request)
	{
		logger.info("IOException handler executed");
		return "controller_error"; 
	}
}