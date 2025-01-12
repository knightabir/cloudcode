package com.cloudcode.auth.controller;

import com.cloudcode.auth.Model.User;
import com.cloudcode.auth.Security.jwt.JwtUtils;
import com.cloudcode.auth.service.AuthService;
import com.cloudcode.auth.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            if (userService.isUserExists(user.getEmail())) {
                String token = jwtUtils.generateToken(user.getEmail());
                return ResponseEntity.ok(Map.of("token", token));
            }
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader String Authorization) {
        try {
            if (Authorization == null || Authorization.isBlank()) {
                return ResponseEntity.status(400).body(Map.of("error", "Request Header is missing"));
            }
            String refreshToken = Authorization.substring("Bearer ".length());
            try {
                System.out.println(refreshToken);
                if (jwtUtils.validateToken(refreshToken)) {
                    String newToken = jwtUtils.generateToken(jwtUtils.extractUsername(refreshToken));
                    return ResponseEntity.ok(Map.of("token", newToken));
                }
                return ResponseEntity.status(400).body(Map.of("error", "Invalid refresh token"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage(), "message", "Internal server error."));
        }
    }

}