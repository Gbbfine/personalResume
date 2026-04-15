package com.personalresume.domain.contact;

import com.personalresume.common.entity.SortableVisibleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contact")
public class Contact extends SortableVisibleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(nullable = false, length = 32)
    private String type;

    @Column(nullable = false, length = 100)
    private String label;

    @Column(nullable = false, length = 255)
    private String value;
}