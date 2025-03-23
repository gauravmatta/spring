package com.springimplant.complaintmanager.controller;

import com.springimplant.complaintmanager.config.Utils;
import com.springimplant.complaintmanager.dao.ComplaintDao;
import com.springimplant.complaintmanager.entities.Complaint;
import com.springimplant.complaintmanager.pojo.Alien;
import jakarta.servlet.http.HttpSession;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/home")
@PropertySource({"classpath:admin-properties.properties"})
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private Environment env;
	
	@Autowired
	Utils utils;
	
	
   @GetMapping("/helloworld")
   public String helloWorld(@RequestParam("name") String myName,HttpSession session,Model model)
   {
	  System.out.println("Hello "+myName);
	  session.setAttribute("name", myName);
	  model.addAttribute("mname", myName);
	  return "home";
   }
   
   @GetMapping("/helloalien")
   public String helloAlien(Alien alien,HttpSession session,Model model)
   {
	  System.out.println("Hello "+alien);
	  session.setAttribute("name", alien);
	  model.addAttribute("obj", alien);
	  return "home";
   }
   
   @GetMapping("/fileComplaint")
   public String fileComplaint()
   {
   	Complaint complaint=new Complaint("Hello","Gaurav","gaurav@mail.com");
   	Complaint complaint1=new Complaint("Test Message","Test","test@test.com");
   	ComplaintDao complaintDao=new ComplaintDao(sessionFactory);
   	complaintDao.insertComplaint(complaint);
   	complaintDao.insertComplaint(complaint1);
   	return "fileComplaint";
   }
   
   @PostMapping("/submitComplaint")
   public String submitComplaint(
   		@RequestParam String complaint,
   		@RequestParam String name, 
   		@RequestParam String email)
   {
   	System.out.println(complaint);
   	ComplaintDao dao=new ComplaintDao(sessionFactory);
   	Complaint comp=new Complaint(complaint,name,email);
   	dao.insertComplaint(comp);
   	return "submitComplaint";
   }
   
   @GetMapping("/showComplaints")
   public String showComplaints()
   {
   	System.out.println("Here I am");
   	return "showEnterPassword";
   }
   
   @PostMapping("/showComplaints")
   public ModelAndView showComplaintsPost(@RequestParam String pass,ModelAndView modelAndView)
   {
   	String syspass=env.getProperty("admin.password");
   	String encodedPass=utils.md5Java(pass);
   	if(encodedPass.equals(syspass))
   	{
    	ComplaintDao complaintDao=new ComplaintDao(sessionFactory);
    	List<Complaint> complaints=complaintDao.getAllComplaints();
    	modelAndView.addObject("complaints", complaints);
    	modelAndView.setViewName("showComplaints");
       	for(Complaint c:complaintDao.getAllComplaints())
       	{
       		System.out.println(c);
       	}
   	}
   	else
   	{
   		modelAndView.setViewName("showEnterPasswords");
   	}
   	
   	return modelAndView;
   	
   }
   
   
	@GetMapping("/")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}