package com.personalresume.domain.education;

import com.personalresume.common.entity.SortableVisibleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "education_experience")
public class EducationExperience extends SortableVisibleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(nullable = false, length = 120)
    private String school;

    @Column(nullable = false, length = 50)
    private String degree;

    @Column(nullable = false, length = 80)
    private String major;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(length = 32)
    private String gpa;
}