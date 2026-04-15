package com.personalresume.domain.skill;

import com.personalresume.common.entity.SortableVisibleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "skill")
public class Skill extends SortableVisibleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(nullable = false, length = 100)
    private String name;
}