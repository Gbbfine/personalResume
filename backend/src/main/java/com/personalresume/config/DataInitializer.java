package com.personalresume.config;

import com.personalresume.domain.admin.AdminUser;
import com.personalresume.domain.admin.AdminUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default-username}")
    private String defaultUsername;

    @Value("${app.admin.default-password}")
    private String defaultPassword;

    public DataInitializer(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        adminUserRepository.findByUsername(defaultUsername).ifPresentOrElse(user -> {
            if (!"ACTIVE".equals(user.getStatus())) {
                user.setStatus("ACTIVE");
                adminUserRepository.save(user);
            }
        }, () -> {
            AdminUser admin = new AdminUser();
            admin.setUsername(defaultUsername);
            admin.setPasswordHash(passwordEncoder.encode(defaultPassword));
            admin.setStatus("ACTIVE");
            adminUserRepository.save(admin);
        });
    }
}
