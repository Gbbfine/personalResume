package com.personalresume.domain.education;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationExperienceRepository extends JpaRepository<EducationExperience, Long> {
    List<EducationExperience> findByIsVisibleTrue(Sort sort);
    List<EducationExperience> findByProfileIdAndIsVisibleTrue(Long profileId, Sort sort);
}
