package com.springimplant.jwt.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springimplant.jwt.api.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByUserName(String username);

}
