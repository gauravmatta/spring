package com.springimplant.complaintmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.springimplant.complaintmanager.entities.Users;

@RepositoryRestResource(collectionResourceRel = "repousers",path = "repousers")
public interface UsersRestRepository extends JpaRepository<Users, Integer> {

}
