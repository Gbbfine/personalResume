package com.personalresume.auth;

import com.personalresume.auth.dto.LoginRequest;
import com.personalresume.auth.dto.LoginResponse;
import com.personalresume.common.exception.UnauthorizedException;
import com.personalresume.config.JwtTokenProvider;
import com.personalresume.domain.admin.AdminUser;
import com.personalresume.domain.admin.AdminUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.jwt.expiration-minutes}")
    private long expirationMinutes;

    public AuthService(AdminUserRepository adminUserRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        AdminUser user = adminUserRepository.findByUsername(request.username())
                .orElseThrow(() -> new UnauthorizedException("用户名或密码错误"));

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new UnauthorizedException("账号不可用");
        }

        boolean matched = passwordEncoder.matches(request.password(), user.getPasswordHash());
        if (!matched) {
            throw new UnauthorizedException("用户名或密码错误");
        }

        user.setLastLoginAt(LocalDateTime.now());
        adminUserRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername());
        return new LoginResponse(token, "Bearer", expirationMinutes);
    }
}
