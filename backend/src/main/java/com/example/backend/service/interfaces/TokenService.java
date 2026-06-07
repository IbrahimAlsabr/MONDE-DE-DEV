package com.example.backend.service.interfaces;

import org.springframework.security.core.Authentication;

public interface TokenService {

	String generateToken(Authentication authentication);

	String generateRefreshToken(Authentication authentication);

	Authentication getAuthentication(String token);
}
