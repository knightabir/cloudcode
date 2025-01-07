package com.cloudcode.service;

import com.cloudcode.Model.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(String userId, User user) throws ChangeSetPersister.NotFoundException;

    List<User> getAllUser();

    void deleteUser(String id);

    boolean isUserExists(String email);

}