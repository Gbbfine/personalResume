package com.personalresume.admin.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EducationExperienceRequest(
        @Min(1) Long profileId,
        @NotBlank(message = "school cannot be blank") @Size(max = 120) String school,
        @NotBlank(message = "degree cannot be blank") @Size(max = 50) String degree,
        @NotBlank(message = "major cannot be blank") @Size(max = 80) String major,
        @NotNull LocalDate startDate,
        LocalDate endDate,
        @Size(max = 32) String gpa,
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