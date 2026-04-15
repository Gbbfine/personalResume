package com.personalresume.admin;

import com.personalresume.admin.dto.WorkExperienceRequest;
import com.personalresume.common.ApiResponse;
import com.personalresume.common.SortUtils;
import com.personalresume.common.exception.ResourceNotFoundException;
import com.personalresume.domain.profile.Profile;
import com.personalresume.domain.profile.ProfileRepository;
import com.personalresume.domain.work.WorkExperience;
import com.personalresume.domain.work.WorkExperienceRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/work-experiences")
public class WorkExperienceAdminController {

    private final WorkExperienceRepository repository;
    private final ProfileRepository profileRepository;

    public WorkExperienceAdminController(WorkExperienceRepository repository, ProfileRepository profileRepository) {
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
                .orElseThrow(() -> new ResourceNotFoundException("work experience not found")));
    }

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody WorkExperienceRequest request) {
        WorkExperience entity = new WorkExperience();
        map(entity, request, resolveProfileId(request.profileId(), null));
        return ApiResponse.success(repository.save(entity));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody WorkExperienceRequest request) {
        WorkExperience entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("work experience not found"));
        map(entity, request, resolveProfileId(request.profileId(), entity.getProfileId()));
        return ApiResponse.success(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("work experience not found");
        }
        repository.deleteById(id);
        return ApiResponse.success();
    }

    private void map(WorkExperience entity, WorkExperienceRequest request, Long profileId) {
        entity.setProfileId(profileId);
        entity.setCompany(request.company());
        entity.setPosition(request.position());
        entity.setType(request.type());
        entity.setStartDate(request.startDate());
        entity.setEndDate(request.endDate());
        entity.setDescription(request.description());
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