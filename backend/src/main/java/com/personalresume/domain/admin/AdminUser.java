package com.personalresume.domain.admin;

import com.personalresume.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "admin_user")
public class AdminUser extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 16)
    private String status = "ACTIVE";

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
}
