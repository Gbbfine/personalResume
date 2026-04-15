package com.personalresume.admin;

import com.personalresume.admin.dto.EducationExperienceRequest;
import com.personalresume.common.ApiResponse;
import com.personalresume.common.SortUtils;
import com.personalresume.common.exception.ResourceNotFoundException;
import com.personalresume.domain.education.EducationExperience;
import com.personalresume.domain.education.EducationExperienceRepository;
import com.personalresume.domain.profile.Profile;
import com.personalresume.domain.profile.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/educations")
public class EducationExperienceAdminController {

    private final EducationExperienceRepository repository;
    private final ProfileRepository profileRepository;

    public EducationExperienceAdminController(EducationExperienceRepository repository, ProfileRepository profileRepository) {
        this.repository = repository;
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.success(repository.findAll(SortUtils.bySortOrderAndUpdatedAt()));
    }

    @GetMapping("/{id}")
    public ApiResponse<?> detail(@PathVariable Long id) {
        return ApiResponse.success(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("education not found")));
    }

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody EducationExperienceRequest request) {
        EducationExperience entity = new EducationExperience();
        map(entity, request, resolveProfileId(request.profileId(), null));
        return ApiResponse.success(repository.save(entity));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody EducationExperienceRequest request) {
        EducationExperience entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("education not found"));
        map(entity, request, resolveProfileId(request.profileId(), entity.getProfileId()));
        return ApiResponse.success(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("education not found");
        }
        repository.deleteById(id);
        return ApiResponse.success();
    }

    private void map(EducationExperience entity, EducationExperienceRequest request, Long profileId) {
        entity.setProfileId(profileId);
        entity.setSchool(request.school());
        entity.setDegree(request.degree());
        entity.setMajor(request.major());
        entity.setStartDate(request.startDate());
        entity.setEndDate(request.endDate());
        entity.setGpa(request.gpa());
        entity.setSortOrder(AdminFieldDefaults.sortOrder(request.sortOrder()));
        entity.setIsVisible(AdminFieldDefaults.isVisible(request.isVisible()));
    }

    private Long resolveProfileId(Long requestProfileId, Long currentProfileId) {
        if (requestProfileId != null) {
            if (!profileRepository.existsById(requestProfileId)) {
                throw new ResourceNotFoundException("profile not found");
            }
            return requestProfileId;
        }

        if (currentProfileId != null) {
            return currentProfileId;
        }

        return profileRepository.findTopByIsActiveTrueOrderByUpdatedAtDesc()
                .or(() -> profileRepository.findTopByOrderByUpdatedAtDesc())
                .map(Profile::getId)
                .orElseThrow(() -> new ResourceNotFoundException("active profile not found"));
    }
}