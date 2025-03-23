package com.springimplant.complaintmanager.controller;

import com.springimplant.complaintmanager.dao.UsersRepository;
import com.springimplant.complaintmanager.entities.Users;
import com.springimplant.complaintmanager.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

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
	
	@GetMapping(path="/list")
	@ResponseBody
	public List<Users> getUsers() {
		return usersRepository.findAll();
	}
	
	@GetMapping(path="/get/{id}",produces = {"application/json"})
	@ResponseBody
	public ResponseEntity<?> getUser(@PathVariable("id") int id) {
			Users user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + id));
			return ResponseEntity.ok(user);
	}
	
	@PostMapping(path="/adduser",consumes = {"application/json"})
	@ResponseBody
	public Users addRestUser(@RequestBody Users user) {
		System.out.println(user);
		usersRepository.save(user);
		return user;
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public Users deleteUser(@PathVariable int id) {
	Users user =usersRepository.getReferenceById(id);
	usersRepository.delete(user);
	return user;
	}
	
	@PutMapping(path="/user",consumes = {"application/json"})
	@ResponseBody
	public Users updateRestUser(@RequestBody Users user) {
		System.out.println(user);
		usersRepository.save(user);
		return user;
	}

}
