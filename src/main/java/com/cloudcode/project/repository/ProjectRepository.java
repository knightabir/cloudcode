package com.cloudcode.project.repository;

import com.cloudcode.project.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByCompanyId(String companyId);
}
