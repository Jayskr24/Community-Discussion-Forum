package com.community.backend.repository;

import com.community.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // MongoDB auto query generation handler mapping
    User findByEmail(String email);
}