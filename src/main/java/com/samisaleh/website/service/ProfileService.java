package com.samisaleh.website.service;

import com.samisaleh.website.dto.ProfileDtos.*;
import com.samisaleh.website.model.User;
import com.samisaleh.website.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileResponse getProfile(String username) {
        User user = findUserByUsername(username);
        return new ProfileResponse(
                user.getUsername(),
                user.getRole().toString(),
                user.getCreatedAt().toString(),
                user.getBio(),
                user.getDisplayName()
        );
    }

    @Transactional
    public ProfileResponse updateProfile(String username, UpdateProfileRequest request) {
        User user = findUserByUsername(username);

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        // Update password if provided
        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        // Update other fields
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }

        if (request.getDisplayName() != null) {
            user.setDisplayName(request.getDisplayName());
        }

        User updatedUser = userRepository.save(user);
        return new ProfileResponse(
                updatedUser.getUsername(),
                updatedUser.getRole().toString(),
                updatedUser.getCreatedAt().toString(),
                updatedUser.getBio(),
                updatedUser.getDisplayName()
        );
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}