package com.cloudcode.session.controller;

import com.cloudcode.session.model.Session;
import com.cloudcode.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;


    @GetMapping
    public ResponseEntity<?> getAllSessions() {
        try {
            return ResponseEntity.ok(sessionService.getAllSessions());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(sessionService.getSessionById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getSessionByUserId(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(sessionService.getSessionByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getSessionByProjectId(@PathVariable String projectId) {
        try {
            return ResponseEntity.ok(sessionService.getSessionByProjectId(projectId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody Session session) {
        try {
            return ResponseEntity.ok(sessionService.createSession(session));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<?> endSession(@PathVariable String id) {
        try {
            var data = sessionService.endSession(id);
            return ResponseEntity.ok(Map.of("message", "Session ended successfully", "data", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable String id) {
        try {
            sessionService.deleteSession(id);
            return ResponseEntity.ok(Map.of("message", "Session deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

}
