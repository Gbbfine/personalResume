package com.personalresume.domain.project;

import com.personalresume.common.entity.SortableVisibleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "project_experience")
public class ProjectExperience extends SortableVisibleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 80)
    private String role;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String highlight;

    @Column(name = "demo_url", length = 255)
    private String demoUrl;

    @Column(name = "repo_url", length = 255)
    private String repoUrl;
}