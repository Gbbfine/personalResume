package com.personalresume.domain.profile;

import com.personalresume.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(length = 120)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "resume_pdf_url", length = 255)
    private String resumePdfUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;
}