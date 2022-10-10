package com.project.adminmanagementmicroservice.repository;

import com.project.adminmanagementmicroservice.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin,Integer> {

    @Query("{adminEmail:?0}")
    Admin findByEmail(String email);

    @Query("{adminEmail:?0}")
    Admin existsByEmail(String email);

}
