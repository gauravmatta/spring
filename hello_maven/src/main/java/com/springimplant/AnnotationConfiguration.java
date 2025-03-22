package com.springimplant;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationConfiguration {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Vehicle beanObj = (Vehicle) context.getBean("annotatedCar");
        beanObj.drive();
    }
}
