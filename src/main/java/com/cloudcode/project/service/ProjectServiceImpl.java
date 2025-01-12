package com.cloudcode.project.service;

import com.cloudcode.auth.Security.CurrentUser;
import com.cloudcode.project.model.Project;
import com.cloudcode.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public List<Project> getAllProjects() {
        try {
            return projectRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project getProjectById(String id) {
        try {
            return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Project> getAllProjectsByCompanyId(String companyId) {
        try {
            return projectRepository.findByCompanyId(companyId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project createProject(Project project) {
        try {
            project.setCreatedAt(System.currentTimeMillis());
            project.setCompany(currentUser.getCurrentUserCompany());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project updateProject(String id, Project project) {
        try {
            Project exisitngProject = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
            project.setName(exisitngProject.getName());
            project.setDescription(exisitngProject.getDescription());
            project.setUpdatedAt(System.currentTimeMillis());
            project.setContributors(exisitngProject.getContributors());
            return projectRepository.save(project);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProject(String id) {
        try {
            projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
            projectRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
