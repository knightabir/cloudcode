package com.cloudcode.resource.service;

import com.cloudcode.auth.Security.CurrentUser;
import com.cloudcode.resource.model.Resource;
import com.cloudcode.resource.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public List<Resource> getAllResources() {
        try {
            return resourceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource getResourceById(String id) {
        try {
            return resourceRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Resource> getResourcesByUserId(String userId) {
        try {
            return resourceRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Resource> getResourcesByProjectId(String projectId) {
        try {
            return resourceRepository.findByProjectId(projectId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Resource> getActiveResources() {
        try {
            return resourceRepository.findByIsActive(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource createResource(Resource resource) {
        try {
            resource.setActive(true);
            resource.setCompanyId(currentUser.getCurrentUserCompany().getId());
            resource.setEditorUrl(generateEditorUrl(resource.getContainerId()));
            return resourceRepository.save(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource deactivateResource(String id) {
        try {
            Resource resource = resourceRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
            resource.setActive(false);
            return resourceRepository.save(resource);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource activateResource(String id) {
        try {
            Resource resource = resourceRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
            resource.setActive(true);
            return resourceRepository.save(resource);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteResource(String id) {
        try {
            resourceRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateEditorUrl(String containerId) {
        try {
            return "https://editor.cloudcode.com/" + containerId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
