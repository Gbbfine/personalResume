package com.personalresume.admin;

import com.personalresume.admin.dto.ContactRequest;
import com.personalresume.common.ApiResponse;
import com.personalresume.common.SortUtils;
import com.personalresume.common.exception.ResourceNotFoundException;
import com.personalresume.domain.contact.Contact;
import com.personalresume.domain.contact.ContactRepository;
import com.personalresume.domain.profile.Profile;
import com.personalresume.domain.profile.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/contacts")
public class ContactAdminController {

    private final ContactRepository repository;
    private final ProfileRepository profileRepository;

    public ContactAdminController(ContactRepository repository, ProfileRepository profileRepository) {
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
                .orElseThrow(() -> new ResourceNotFoundException("contact not found")));
    }

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody ContactRequest request) {
        Contact entity = new Contact();
        map(entity, request, resolveProfileId(request.profileId(), null));
        return ApiResponse.success(repository.save(entity));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody ContactRequest request) {
        Contact entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("contact not found"));
        map(entity, request, resolveProfileId(request.profileId(), entity.getProfileId()));
        return ApiResponse.success(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("contact not found");
        }
        repository.deleteById(id);
        return ApiResponse.success();
    }

    private void map(Contact entity, ContactRequest request, Long profileId) {
        entity.setProfileId(profileId);
        entity.setType(request.type());
        entity.setLabel(request.label());
        entity.setValue(request.value());
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