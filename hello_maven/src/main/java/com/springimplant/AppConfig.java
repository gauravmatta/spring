package com.springimplant;

import com.springimplant.annotation.MobileProcessor;
import com.springimplant.annotation.Samsung;
import com.springimplant.annotation.Snapdragon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Samsung getPhone(){
        return new Samsung();
    }

    @Bean
    public MobileProcessor getProcessor(){
        return new Snapdragon();
    }
}
