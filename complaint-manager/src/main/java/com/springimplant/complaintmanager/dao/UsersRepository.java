package com.springimplant.complaintmanager.dao;

import com.springimplant.complaintmanager.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

	List<Users> findByTech(String tech);
	
	List<Users> findByIdGreaterThan(int id);

	@Query("from Users where tech=?1 order by name")
	List<Users> findByTechSorted(String tech);
}
