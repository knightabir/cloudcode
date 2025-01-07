package com.cloudcode.repository;

import com.cloudcode.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    User findUserByEmail(String email);
}
