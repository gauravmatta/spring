package com.springimplant.complaintmanager.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/student")
public class StudentController {
	
	@GetMapping(value = "/getStudent")
	@Operation(
		tags = "Get Student API",
		description = "Get Single Student",
		responses = {
				@ApiResponse(
						responseCode = "200",
						description = "Success"
						),
				@ApiResponse(
						responseCode = "500",
						description = "Internal Error"
						)
		}
	)
	public String getStudent() {
		
		return "Student...";
	}
	
	@GetMapping(value = "/getAllStudent")
	public String getAllStudent() {
		
		return "Student All...";
	}
	
	@PostMapping(value = "/register")
	public String registerStudent() {
		
		return "Student Register...";
	}
	
	@Hidden
	@DeleteMapping(value = "/deleteStudent")
	public String deleteStudent() {
		
		return "Delete Student...";
	}
	
	@PutMapping(value = "/updateStudent")
	public String updateStudent() {
		
		return "Update Student...";
	}
}
