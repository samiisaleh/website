package com.samisaleh.website.service;

import com.samisaleh.website.dto.ProjectDtos.*;
import com.samisaleh.website.model.ProjectEntry;
import com.samisaleh.website.model.User;
import com.samisaleh.website.repository.ProjectRepository;
import com.samisaleh.website.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToProjectResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(this::mapToProjectResponse)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
    }

    @Transactional
    public ProjectResponse createProject(String username, CreateProjectRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ProjectEntry project = new ProjectEntry();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setTechnologies(request.getTechnologies());
        project.setImageUrl(request.getImageUrl());
        project.setProjectUrl(request.getProjectUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setCreatedBy(user);

        ProjectEntry savedProject = projectRepository.save(project);
        return mapToProjectResponse(savedProject);
    }

    @Transactional
    public ProjectResponse updateProject(Long id, String username, UpdateProjectRequest request) {
        ProjectEntry project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        // Check if user is the owner of the project
        if (!project.getCreatedBy().getUsername().equals(username)) {
            throw new IllegalStateException("You don't have permission to update this project");
        }

        if (request.getTitle() != null) {
            project.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getTechnologies() != null) {
            project.setTechnologies(request.getTechnologies());
        }
        if (request.getImageUrl() != null) {
            project.setImageUrl(request.getImageUrl());
        }
        if (request.getProjectUrl() != null) {
            project.setProjectUrl(request.getProjectUrl());
        }
        if (request.getGithubUrl() != null) {
            project.setGithubUrl(request.getGithubUrl());
        }

        ProjectEntry updatedProject = projectRepository.save(project);
        return mapToProjectResponse(updatedProject);
    }

    @Transactional
    public void deleteProject(Long id, String username) {
        ProjectEntry project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        if (!project.getCreatedBy().getUsername().equals(username)) {
            throw new IllegalStateException("You don't have permission to delete this project");
        }

        projectRepository.delete(project);
    }

    private ProjectResponse mapToProjectResponse(ProjectEntry project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setTechnologies(project.getTechnologies());
        response.setImageUrl(project.getImageUrl());
        response.setProjectUrl(project.getProjectUrl());
        response.setGithubUrl(project.getGithubUrl());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());
        response.setCreatedBy(project.getCreatedBy().getUsername());
        return response;
    }
}