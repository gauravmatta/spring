package com.springimplant.complaintmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {
	
	String addressBaseUrl = "google.co.in";
	
    @Bean
    WebClient webClient() {
	return WebClient.builder().build();
	}

}
