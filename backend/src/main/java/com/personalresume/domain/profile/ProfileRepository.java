package com.personalresume.domain.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findTopByIsActiveTrueOrderByUpdatedAtDesc();

    Optional<Profile> findTopByOrderByUpdatedAtDesc();

    boolean existsByIsActiveTrue();

    @Modifying
    @Query("update Profile p set p.isActive = false where p.id <> :activeId and p.isActive = true")
    int deactivateOtherProfiles(@Param("activeId") Long activeId);

    @Modifying
    @Query("update Profile p set p.isActive = false where p.isActive = true")
    int deactivateAllProfiles();

    @Modifying
    @Query("update Profile p set p.isActive = true where p.id = :activeId")
    int activateProfile(@Param("activeId") Long activeId);
}