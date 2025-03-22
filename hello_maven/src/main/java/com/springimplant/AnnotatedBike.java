package com.springimplant;

import org.springframework.stereotype.Component;

@Component
public class AnnotatedBike implements Vehicle {

    public void drive(){
        System.out.println("Bhag Rahi Hai");
    }
}
