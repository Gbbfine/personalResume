package com.personalresume.domain.work;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    List<WorkExperience> findByIsVisibleTrue(Sort sort);
    List<WorkExperience> findByProfileIdAndIsVisibleTrue(Long profileId, Sort sort);
}
