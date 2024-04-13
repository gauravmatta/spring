package com.springimplant.complaintmanager.controller;

import java.security.Principal;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.persistence.Entity;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AppController
{
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	private WebClient webClient;
	
    @GetMapping("/helloworld")
    public String helloWorld()
    {
    	log.info("Hello World ");
    	return "HelloWorld";
    }
    
    @GetMapping("/")
    public String home(Principal principal)
    {
    	log.info("Root ");
    	return "Hello, "+ principal.getName();
    }
    
    @GetMapping("/webclient")
    public String webClient(@RequestParam String name) {
    	log.info("Webclient End Point Called");
    	return webClient.get().uri("google.co.in")
    			.retrieve().toEntity(String.class)
    			.doOnNext(entity -> log.info("Response status: {}",entity))
    			.mapNotNull(HttpEntity::getBody)
    			.block();
    }
}