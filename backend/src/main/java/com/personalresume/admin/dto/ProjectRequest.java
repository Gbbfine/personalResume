package com.personalresume.admin.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjectRequest(
        @Min(1) Long profileId,
        @NotBlank(message = "name cannot be blank") @Size(max = 120) String name,
        @NotBlank(message = "role cannot be blank") @Size(max = 80) String role,
        String summary,
        @NotNull LocalDate startDate,
        LocalDate endDate,
        String highlight,
        @Size(max = 255) String demoUrl,
        @Size(max = 255) String repoUrl,
        @Min(0) Integer sortOrder,
        Boolean isVisible
) {
    @AssertTrue(message = "startDate cannot be after endDate")
    public boolean isDateRangeValid() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return !startDate.isAfter(endDate);
    }
}