package com.springimplant;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Car car = new Car();
        car.drive();
        Bike bike = new Bike();
        bike.drive();
        Vehicle obj = new Car();
        obj.drive();

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Vehicle beanObj = (Vehicle) context.getBean("vehicle");
        beanObj.drive();
    }
}
