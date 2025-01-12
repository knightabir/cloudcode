package com.cloudcode.project.service;

import com.cloudcode.project.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    Project getProjectById(String id);

    List<Project> getAllProjectsByCompanyId(String companyId);

    Project createProject(Project project);

    Project updateProject(String id, Project project);

    void deleteProject(String id);
}
