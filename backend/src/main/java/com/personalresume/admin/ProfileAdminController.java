package com.personalresume.admin;

import com.personalresume.admin.dto.ProfileRequest;
import com.personalresume.common.ApiResponse;
import com.personalresume.common.SortUtils;
import com.personalresume.common.exception.ResourceNotFoundException;
import com.personalresume.domain.profile.Profile;
import com.personalresume.domain.profile.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/profiles")
public class ProfileAdminController {

    private final ProfileRepository profileRepository;

    public ProfileAdminController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.success(profileRepository.findAll(SortUtils.byUpdatedAtDesc()));
    }

    @GetMapping("/{id}")
    public ApiResponse<?> detail(@PathVariable Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("profile not found"));
        return ApiResponse.success(profile);
    }

    @PostMapping
    @Transactional
    public ApiResponse<?> create(@Valid @RequestBody ProfileRequest request) {
        Profile profile = new Profile();
        map(profile, request);

        Profile saved = profileRepository.save(profile);

        if (Boolean.TRUE.equals(request.isActive())) {
            activateProfile(saved.getId());
            saved = profileRepository.findById(saved.getId()).orElse(saved);
        }

        return ApiResponse.success(saved);
    }

    @PutMapping("/{id}")
    @Transactional
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody ProfileRequest request) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("profile not found"));

        map(profile, request);

        if (request.isActive() != null) {
            profile.setIsActive(request.isActive());
        }

        Profile saved = profileRepository.save(profile);

        if (Boolean.TRUE.equals(request.isActive())) {
            activateProfile(saved.getId());
            saved = profileRepository.findById(saved.getId()).orElse(saved);
        }

        return ApiResponse.success(saved);
    }

    @PutMapping("/{id}/activate")
    @Transactional
    public ApiResponse<?> activate(@PathVariable Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("profile not found"));

        activateProfile(id);
        profile.setIsActive(true);
        return ApiResponse.success(profile);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ApiResponse<Void> delete(@PathVariable Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("profile not found"));

        boolean wasActive = Boolean.TRUE.equals(profile.getIsActive());
        profileRepository.deleteById(id);

        if (wasActive) {
            ensureOneActiveProfile();
        }

        return ApiResponse.success();
    }

    private void map(Profile profile, ProfileRequest request) {
        profile.setFullName(request.fullName());
        profile.setTitle(request.title());
        profile.setAvatarUrl(request.avatarUrl());
        profile.setLocation(request.location());
        profile.setBio(request.bio());
        profile.setResumePdfUrl(request.resumePdfUrl());

        if (request.isActive() != null) {
            profile.setIsActive(request.isActive());
        }
    }

    private void activateProfile(Long id) {
        profileRepository.deactivateOtherProfiles(id);
        profileRepository.activateProfile(id);
    }

    private void ensureOneActiveProfile() {
        if (profileRepository.existsByIsActiveTrue()) {
            return;
        }

        profileRepository.findTopByOrderByUpdatedAtDesc().ifPresent(latest ->
                profileRepository.activateProfile(latest.getId())
        );
    }
}