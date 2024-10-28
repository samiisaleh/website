package com.samisaleh.website.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

public class ProjectDtos {

    @Data
    public static class CreateProjectRequest {
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be less than 255 characters")
        private String title;

        @NotBlank(message = "Description is required")
        private String description;

        @NotEmpty(message = "At least one technology must be specified")
        private String[] technologies;

        private String imageUrl;
        private String projectUrl;
        private String githubUrl;
    }

    @Data
    public static class UpdateProjectRequest {
        @Size(max = 255, message = "Title must be less than 255 characters")
        private String title;
        private String description;
        private String[] technologies;
        private String imageUrl;
        private String projectUrl;
        private String githubUrl;
    }

    @Data
    public static class ProjectResponse {
        private Long id;
        private String title;
        private String description;
        private String[] technologies;
        private String imageUrl;
        private String projectUrl;
        private String githubUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
    }
}