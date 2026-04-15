package com.personalresume.admin.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record WorkExperienceRequest(
        @Min(1) Long profileId,
        @NotBlank(message = "company cannot be blank") @Size(max = 120) String company,
        @NotBlank(message = "position cannot be blank") @Size(max = 80) String position,
        @NotBlank(message = "type cannot be blank") @Size(max = 32) String type,
        @NotNull LocalDate startDate,
        LocalDate endDate,
        String description,
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