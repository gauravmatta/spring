package com.springimplant.complaintmanager.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Scope(value = "prototype")
public class Alien {

	private int aid;
	private String name;
	private String tech;
	@Autowired
	@Qualifier("lap1")
	private Laptop laptop;

	public Alien(){
		super();
		System.out.println("Alien Object Created");
	}

	public void show(){
		System.out.println("In Show and "+laptop.toString());
	}
}
