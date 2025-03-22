package com.springimplant.annotatedInjection;

import org.springframework.stereotype.Component;

@Component
public class AnnotatedTyre {
    private String brand;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return "AnnotatedTyre{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
