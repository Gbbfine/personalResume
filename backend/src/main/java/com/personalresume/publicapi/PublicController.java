package com.personalresume.publicapi;

import com.personalresume.common.ApiResponse;
import com.personalresume.common.SortUtils;
import com.personalresume.domain.contact.ContactRepository;
import com.personalresume.domain.education.EducationExperienceRepository;
import com.personalresume.domain.honor.HonorRepository;
import com.personalresume.domain.profile.Profile;
import com.personalresume.domain.profile.ProfileRepository;
import com.personalresume.domain.project.ProjectExperienceRepository;
import com.personalresume.domain.skill.SkillRepository;
import com.personalresume.domain.work.WorkExperienceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final ProjectExperienceRepository projectRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationExperienceRepository educationRepository;
    private final HonorRepository honorRepository;
    private final ContactRepository contactRepository;

    public PublicController(ProfileRepository profileRepository,
                            SkillRepository skillRepository,
                            ProjectExperienceRepository projectRepository,
                            WorkExperienceRepository workExperienceRepository,
                            EducationExperienceRepository educationRepository,
                            HonorRepository honorRepository,
                            ContactRepository contactRepository) {
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
        this.projectRepository = projectRepository;
        this.workExperienceRepository = workExperienceRepository;
        this.educationRepository = educationRepository;
        this.honorRepository = honorRepository;
        this.contactRepository = contactRepository;
    }

    @GetMapping("/profile")
    public ApiResponse<?> profile() {
        return ApiResponse.success(findPublicProfile().orElse(null));
    }

    @GetMapping("/skills")
    public ApiResponse<?> skills() {
        Long profileId = findPublicProfileId();
        if (profileId == null) {
            return ApiResponse.success(List.of());
        }
        return ApiResponse.success(skillRepository.findByProfileIdAndIsVisibleTrue(profileId, SortUtils.bySortOrderAndUpdatedAt()));
    }

    @GetMapping("/projects")
    public ApiResponse<?> projects() {
        Long profileId = findPublicProfileId();
        if (profileId == null) {
            return ApiResponse.success(List.of());
        }
        return ApiResponse.success(projectRepository.findByProfileIdAndIsVisibleTrue(profileId, SortUtils.bySortOrderAndUpdatedAt()));
    }

    @GetMapping("/work-experiences")
    public ApiResponse<?> workExperiences() {
        Long profileId = findPublicProfileId();
        if (profileId == null) {
            return ApiResponse.success(List.of());
        }
        return ApiResponse.success(workExperienceRepository.findByProfileIdAndIsVisibleTrue(profileId, SortUtils.bySortOrderAndUpdatedAt()));
    }

    @GetMapping("/educations")
    public ApiResponse<?> educations() {
        Long profileId = findPublicProfileId();
        if (profileId == null) {
            return ApiResponse.success(List.of());
        }
        return ApiResponse.success(educationRepository.findByProfileIdAndIsVisibleTrue(profileId, SortUtils.bySortOrderAndUpdatedAt()));
    }

    @GetMapping("/honors")
    public ApiResponse<?> honors() {
        Long profileId = findPublicProfileId();
        if (profileId == null) {
            return ApiResponse.success(List.of());
        }
        return ApiResponse.success(honorRepository.findByProfileIdAndIsVisibleTrue(profileId, SortUtils.bySortOrderAndUpdatedAt()));
    }

    @GetMapping("/contacts")
    public ApiResponse<?> contacts() {
        Long profileId = findPublicProfileId();
        if (profileId == null) {
            return ApiResponse.success(List.of());
        }
        return ApiResponse.success(contactRepository.findByProfileIdAndIsVisibleTrue(profileId, SortUtils.bySortOrderAndUpdatedAt()));
    }

    private Optional<Profile> findPublicProfile() {
        return profileRepository.findTopByIsActiveTrueOrderByUpdatedAtDesc()
                .or(() -> profileRepository.findTopByOrderByUpdatedAtDesc());
    }

    private Long findPublicProfileId() {
        return findPublicProfile().map(Profile::getId).orElse(null);
    }
}