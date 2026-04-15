package com.personalresume.auth.dto;

public record LoginResponse(String token, String tokenType, long expiresInMinutes) {
}
