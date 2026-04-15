package com.personalresume.domain.work;

import com.personalresume.common.entity.SortableVisibleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "work_experience")
public class WorkExperience extends SortableVisibleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(nullable = false, length = 120)
    private String company;

    @Column(nullable = false, length = 80)
    private String position;

    @Column(nullable = false, length = 32)
    private String type;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String description;
}