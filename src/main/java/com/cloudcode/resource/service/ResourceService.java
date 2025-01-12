package com.cloudcode.resource.service;

import com.cloudcode.resource.model.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> getAllResources();

    Resource getResourceById(String id);

    List<Resource> getResourcesByUserId(String userId);

    List<Resource> getResourcesByProjectId(String projectId);

    List<Resource> getActiveResources();

    Resource createResource(Resource resource);

    Resource deactivateResource(String id);
    Resource activateResource(String id);

    void deleteResource(String id);

    String generateEditorUrl(String containerId);
}
