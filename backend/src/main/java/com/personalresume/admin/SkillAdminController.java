package com.personalresume.admin;

import com.personalresume.admin.dto.SkillRequest;
import com.personalresume.common.ApiResponse;
import com.personalresume.common.SortUtils;
import com.personalresume.common.exception.ResourceNotFoundException;
import com.personalresume.domain.profile.Profile;
import com.personalresume.domain.profile.ProfileRepository;
import com.personalresume.domain.skill.Skill;
import com.personalresume.domain.skill.SkillRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/skills")
public class SkillAdminController {

    private final SkillRepository repository;
    private final ProfileRepository profileRepository;

    public SkillAdminController(SkillRepository repository, ProfileRepository profileRepository) {
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
                .orElseThrow(() -> new ResourceNotFoundException("skill not found")));
    }

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody SkillRequest request) {
        Skill entity = new Skill();
        map(entity, request, resolveProfileId(request.profileId(), null));
        return ApiResponse.success(repository.save(entity));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        Skill entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("skill not found"));
        map(entity, request, resolveProfileId(request.profileId(), entity.getProfileId()));
        return ApiResponse.success(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("skill not found");
        }
        repository.deleteById(id);
        return ApiResponse.success();
    }

    private void map(Skill entity, SkillRequest request, Long profileId) {
        entity.setProfileId(profileId);
        entity.setName(request.name());
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