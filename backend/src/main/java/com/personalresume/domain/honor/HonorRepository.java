package com.personalresume.domain.honor;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HonorRepository extends JpaRepository<Honor, Long> {
    List<Honor> findByProfileIdAndIsVisibleTrue(Long profileId, Sort sort);
}