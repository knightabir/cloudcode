package com.cloudcode.auth.service;

import com.cloudcode.auth.Model.User;
import com.cloudcode.auth.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtil;

    public ResponseEntity<String> login(User user) {
        // Validate user credentials (this should be replaced with actual validation logic)
        if (userService.isUserExists(user.getEmail())) {
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    public ResponseEntity<String> logout() {
        // Invalidate the session (token revocation logic can be added)
        return ResponseEntity.ok("Logged out successfully");
    }

    public ResponseEntity<String> refreshToken(String refreshToken) {
        if (jwtUtil.validateToken(refreshToken)) {

            String newToken = jwtUtil.generateToken(jwtUtil.extractUsername(refreshToken));
            return ResponseEntity.ok(newToken);
        }
        return ResponseEntity.status(400).body("Invalid refresh token");
    }
}
