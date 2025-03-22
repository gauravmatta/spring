package com.springimplant.annotatedInjection;

import com.springimplant.XMLInjection.TyreConstructor;
import com.springimplant.XMLInjection.TyreSetter;
import com.springimplant.XMLInjection.Vehicle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationConfiguration {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Vehicle carObj = (Vehicle) context.getBean("annotatedCar");
        carObj.drive();
        Vehicle bikeObj = (Vehicle) context.getBean("annotatedBike");
        bikeObj.drive();

        TyreSetter tyreSetter = (TyreSetter) context.getBean("tyreSetter");
        System.out.println(tyreSetter);
        TyreConstructor tyreConstructed = (TyreConstructor) context.getBean("tyreConstructed");
        System.out.println(tyreConstructed);

        TyreSetter nullTyreSetter = (TyreSetter) context.getBean("nulltyreSetter");
        System.out.println(nullTyreSetter);

        AnnotatedCar car = (AnnotatedCar) context.getBean("annotatedCar");
        car.drive();
    }
}
