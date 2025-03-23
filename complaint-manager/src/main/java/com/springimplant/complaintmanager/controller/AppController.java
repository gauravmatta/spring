package com.springimplant.complaintmanager.controller;

import com.springimplant.complaintmanager.service.MathService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.Map;

@RestController
@Slf4j
@SecurityRequirement(name="auth")
public class AppController
{
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	private WebClient webClient;
	
	@Autowired
	@Qualifier("MathServiceImpl")
	private MathService mathService;
	
    @GetMapping("/helloworld")
    @Operation(
    		tags = "GET Say Hello",
    		description = "Say Hello",
    		responses = {
    				@ApiResponse(
    						description = "Success",
    						responseCode = "200"
    						),
    				@ApiResponse(
    						description = "Data Not Found",
    						responseCode = "404"
    						)
    		}
    		)
    public Map<String,String> helloWorld(@RequestParam(defaultValue = "World") String name)
    {
    	log.trace("Hello {}",name);
    	return Map.of("greeting","Hello "+name);
    }
    
    @GetMapping("/")
    public String home(Principal principal)
    {
    	log.info("Root ");
    	return "Hello, "+ principal.getName();
    }
    
    @Hidden
    @GetMapping("/webclient")
    public String webClient(@RequestParam String name) {
    	log.info("Webclient End Point Called");
    	return webClient.get().uri("google.co.in")
    			.retrieve().toEntity(String.class)
    			.doOnNext(entity -> log.info("Response status: {}",entity))
    			.mapNotNull(HttpEntity::getBody)
    			.block();
    }
    
    @GetMapping("/addnumbers/{fnum}/{snum}")
    public ResponseEntity<Integer> AddNumbers(
    		@PathVariable("fnum") Integer firstNumber,
    		@PathVariable("snum") Integer secondNumber
    		){
    	log.info("Adding Numbers");
    	Integer sum = mathService.addNumbers(firstNumber, secondNumber);
    	return new ResponseEntity<>(sum,HttpStatus.OK);
    }
    
}