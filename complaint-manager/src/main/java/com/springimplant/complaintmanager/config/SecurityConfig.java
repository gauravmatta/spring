package com.springimplant.complaintmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		try {
			return http					
					.authorizeHttpRequests(auth -> {
						auth.requestMatchers("/").permitAll();
						auth.anyRequest().authenticated();
					})
					.csrf(AbstractHttpConfigurer::disable)
					.logout(logout->logout.logoutSuccessUrl("/").permitAll())
					.oauth2Login(withDefaults())
					.formLogin(withDefaults())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
