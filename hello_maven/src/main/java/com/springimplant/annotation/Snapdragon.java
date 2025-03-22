package com.springimplant.annotation;

import org.springframework.stereotype.Component;

@Component
public class Snapdragon implements MobileProcessor{

    @Override
    public void process() {
        System.out.println("World Best CPU");
    }
}
