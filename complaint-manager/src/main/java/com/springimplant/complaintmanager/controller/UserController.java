package com.springimplant.complaintmanager.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.springimplant.complaintmanager.dao.UsersRepository;
import com.springimplant.complaintmanager.entities.Users;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UsersRepository usersRepository;

	@GetMapping("/add")
	public String addUser(Users users) {
		return "addUser";
	}
	
	@GetMapping("/get")
	public String getUser(Users users) {
		return "getUser";
	}
	
	@PostMapping("/add")
	public String saveUser(Users users) {
	Random random = new Random();
	int id = random.nextInt(100);
		users.setId(id);
		usersRepository.save(users);
		return "addUser";
	}
	
	@PostMapping("/get")
	public String searchUser(@RequestParam Integer id,Model model) {
	Users user = usersRepository.findById(id).orElse(new Users());
	System.out.println(user);
	model.addAttribute("users", user);
	return "getUser";
	}
	
	@PostMapping("/getbytech")
	public String searchUserByTech(@RequestParam String tech,Model model) {
	List<Users> user = usersRepository.findByTech(tech);
	List<Users> usersgtThanId = usersRepository.findByIdGreaterThan(63);
	List<Users> usersSorted = usersRepository.findByTechSorted("Java");
	System.out.println(user);
	model.addAttribute("users", user);
	model.addAttribute("usersgtThanId", usersgtThanId);
	model.addAttribute("usersSorted", usersSorted);
	return "getUser";
	}

}
