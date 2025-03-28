package com.springimplant.cloudfunction.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.springimplant.cloudfunction.api.Order;

@Repository
public class OrderDao {
	public List<Order> buildOrders(){
		return Stream.of(
				new Order(101,"Mobile",20000,1),
				new Order(102,"Book",999,1),
				new Order(278,"Book",1466,1),
				new Order(953,"Jeans",4499,1)
				).collect(Collectors.toList());
	}
}
