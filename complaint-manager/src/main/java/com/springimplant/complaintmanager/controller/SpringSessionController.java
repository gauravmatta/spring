package com.springimplant.complaintmanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/session")
public class SpringSessionController {
	@GetMapping("/")
	public String process(Model model,HttpSession session) {
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if (messages == null) {
			messages = new ArrayList<>();
		}
		model.addAttribute("sessionMessages", messages);
		return "index";
	}
	
	@PostMapping("/persistMessage")
	public String persistMessage(@RequestParam("msg") String msg,HttpServletRequest request) {
		List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if(messages==null) {
			messages = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES",messages);
		}
		messages.add(msg);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		return "redirect:/";
	}
	
	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}
