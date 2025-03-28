package com.springimplant.userapi.postgres.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springimplant.userapi.postgres.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	Product findByName(String name);

}
