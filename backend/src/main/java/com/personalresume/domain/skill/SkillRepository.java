package com.personalresume.domain.skill;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByIsVisibleTrue(Sort sort);
    List<Skill> findByProfileIdAndIsVisibleTrue(Long profileId, Sort sort);
}
