package com.personalresume.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(
        @Min(1) Long profileId,
        @NotBlank(message = "type cannot be blank") @Size(max = 32) String type,
        @NotBlank(message = "label cannot be blank") @Size(max = 100) String label,
        @NotBlank(message = "value cannot be blank") @Size(max = 255) String value,
        @Min(0) Integer sortOrder,
        Boolean isVisible
) {
}