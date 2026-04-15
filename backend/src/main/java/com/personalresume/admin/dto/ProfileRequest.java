package com.personalresume.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfileRequest(
        @NotBlank(message = "fullName cannot be blank") @Size(max = 100) String fullName,
        @NotBlank(message = "title cannot be blank") @Size(max = 120) String title,
        @Size(max = 255) String avatarUrl,
        @Size(max = 120) String location,
        String bio,
        @Size(max = 255) String resumePdfUrl,
        Boolean isActive
) {
}