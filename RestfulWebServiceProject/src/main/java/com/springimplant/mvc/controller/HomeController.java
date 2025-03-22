package com.springimplant.mvc.controller;

import com.springimplant.mvc.service.AddService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

	@GetMapping(value="/")
	public String gohome(Model model)
	{
		model.addAttribute("name","Gaurav Matta");
		model.addAttribute("id",37);
		List<String> friends = new ArrayList<>();
		friends.add("Sumit");
		friends.add("Arpit");
		friends.add("Akshay");
		model.addAttribute("friends", friends);
		return "home";
	}

	@RequestMapping("/add")
	public ModelAndView add(@RequestParam("t1") int i,@RequestParam("t2") int j , HttpServletRequest request, HttpServletResponse response){
		int reqI=Integer.parseInt(request.getParameter("t1"));
		int reqJ=Integer.parseInt(request.getParameter("t2"));
		AddService addService = new AddService();
		int k =addService.add(reqI,reqJ);
		int l =addService.add(i,j);
		System.out.println(k);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("display");
		mv.addObject("result",k);
		mv.addObject("reqResult",l);
		return mv;
	}

	@RequestMapping("/addForm")
	public ModelAndView addForm(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("display");
		return mv;
	}
	
	@GetMapping(value="/index")
	public ModelAndView goindex()
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("name","Samuel");
		modelAndView.addObject("rollnumber",12);
		LocalDateTime now = LocalDateTime.now();
		modelAndView.addObject("time",now);
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@GetMapping(value="/help")
	public ModelAndView gohelp()
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("name","Samuel");
		modelAndView.addObject("rollnumber",12);
		LocalDateTime now = LocalDateTime.now();
		modelAndView.addObject("time",now);
		List<String> friends = new ArrayList<>();
		friends.add("Sumit");
		friends.add("Arpit");
		friends.add("Akshay");
		friends.add("Amit");
		modelAndView.addObject("friends", friends);
		modelAndView.setViewName("help");
		return modelAndView;
	}
}
