package com.cloudcode.resource.repository;

import com.cloudcode.resource.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findByUserId(String userId);

    List<Resource> findByProjectId(String projectId);

    List<Resource> findByCompanyId(String companyId);

    List<Resource> findByIsActive(boolean isActive);
}
