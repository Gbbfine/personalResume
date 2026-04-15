package com.personalresume.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SkillRequest(
        @Min(1) Long profileId,
        @NotBlank(message = "name cannot be blank") @Size(max = 100) String name,
        @Min(0) Integer sortOrder,
        Boolean isVisible
) {
}