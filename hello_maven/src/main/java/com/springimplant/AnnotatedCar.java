package com.springimplant;

import org.springframework.stereotype.Component;

@Component
public class AnnotatedCar implements Vehicle{
    public void drive(){
        System.out.println("Chal Rahi hai....");
    }
}
