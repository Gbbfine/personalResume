package com.personalresume.domain.project;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectExperienceRepository extends JpaRepository<ProjectExperience, Long> {
    List<ProjectExperience> findByIsVisibleTrue(Sort sort);
    List<ProjectExperience> findByProfileIdAndIsVisibleTrue(Long profileId, Sort sort);
}
