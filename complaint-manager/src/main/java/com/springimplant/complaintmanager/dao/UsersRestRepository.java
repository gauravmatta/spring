package com.springimplant.complaintmanager.dao;

import com.springimplant.complaintmanager.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "repousers",path = "repousers")
public interface UsersRestRepository extends JpaRepository<Users, Integer> {

}
