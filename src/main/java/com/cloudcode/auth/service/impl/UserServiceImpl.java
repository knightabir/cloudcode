package com.cloudcode.auth.service.impl;

import com.cloudcode.auth.Model.User;
import com.cloudcode.auth.repository.UserRepository;
import com.cloudcode.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        try {
            if (user != null) {
                user.setRoles(List.of("SUPER_ADMIN"));
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Check if there is any user already exists
            if (isUserExists(user.getEmail())) {
                throw new RuntimeException("User already exists");
            }
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User updateUser(String userId, User user) throws ChangeSetPersister.NotFoundException {
        try {
            User existingUser = userRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            if (!user.getPassword().isBlank()) {
                existingUser.setPassword(user.getPassword());
            }
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String id) {
        try {
            if (isUserExists(id)) {
                userRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean isUserExists(String id) {
        return userRepository.existsByEmail(id);
    }
}
