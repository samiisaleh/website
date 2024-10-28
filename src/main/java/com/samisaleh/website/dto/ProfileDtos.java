package com.samisaleh.website.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class ProfileDtos {

    @Data
    public static class UpdateProfileRequest {
        @NotBlank(message = "Current password is required")
        private String currentPassword;

        @Size(min = 8, message = "New password must be at least 8 characters")
        private String newPassword;

        private String bio;

        private String displayName;
    }

    @Data
    public static class ProfileResponse {
        private final String username;
        private final String role;
        private final String createdAt;
        private final String bio;
        private final String displayName;
    }
}