package com.springimplant.complaintmanager.config;

import jakarta.servlet.Filter;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebFilter;
import reactor.util.context.Context;

@Configuration
public class BeanConfiguration {
	
	String addressBaseUrl = "google.co.in";
	
//	 @Bean
//	 Docket api() {
//	        return new Docket(DocumentationType.SWAGGER_2)  
//	          .select()
//	          .apis(RequestHandlerSelectors.any())
//	          .paths(PathSelectors.any())                 
//	          .build();
//	    }
	
    @Bean
    WebClient webClient() {
	return WebClient.builder().build();
	}
    
    @Bean
    Filter correlationFilter() {
    	return(request,response,chain) -> {
    		try {
    			String name = request.getParameter("name");
    			if(name!=null) {
    				MDC.put("cid",name);
    			}
    			chain.doFilter(request, response);
    		} finally {
				MDC.remove("cid");
			}
    	};
    }
    
    @Bean
    WebFilter webCorrelationFilter() {
    	return(exchange,chain) -> {
    		var names = exchange.getRequest().getQueryParams().get("name");
    		if(names != null && !names.isEmpty()) {
    			return chain.filter(exchange).contextWrite(Context.of("cid",names.get(0)));
    		}
    		return chain.filter(exchange);
    	};
    }
}
