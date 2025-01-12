package com.cloudcode.resource.controller;

import com.cloudcode.resource.model.Resource;
import com.cloudcode.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public ResponseEntity<?> getAllResources() {
        try {
            return ResponseEntity.ok(resourceService.getAllResources());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResourceById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(resourceService.getResourceById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getResourcesByUserId(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(resourceService.getResourcesByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getResourcesByProjectId(@PathVariable String projectId) {
        try {
            return ResponseEntity.ok(resourceService.getResourcesByProjectId(projectId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveResources() {
        try {
            return ResponseEntity.ok(resourceService.getActiveResources());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createResource(@RequestBody Resource resource) {
        try {
            return ResponseEntity.ok(resourceService.createResource(resource));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateResource(@PathVariable String id) {
        try {
            return ResponseEntity.ok(resourceService.deactivateResource(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateResource(@PathVariable String id) {
        try {
            return ResponseEntity.ok(resourceService.activateResource(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable String id) {
        try {
            resourceService.deleteResource(id);
            return ResponseEntity.ok(Map.of("message", "Resource deleted successfully"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
