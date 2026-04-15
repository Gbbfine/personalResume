package com.personalresume.domain.honor;

import com.personalresume.common.entity.SortableVisibleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "honor")
public class Honor extends SortableVisibleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 120)
    private String issuer;

    @Column(name = "award_date")
    private LocalDate awardDate;

    @Column(columnDefinition = "TEXT")
    private String description;
}