package com.personalresume.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record HonorRequest(
        @Min(1) Long profileId,
        @NotBlank(message = "title cannot be blank") @Size(max = 120) String title,
        @Size(max = 120) String issuer,
        LocalDate awardDate,
        String description,
        @Min(0) Integer sortOrder,
        Boolean isVisible
) {
}