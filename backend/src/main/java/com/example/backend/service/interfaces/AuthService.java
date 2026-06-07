package com.example.backend.service.interfaces;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RefreshTokenRequest;
import com.example.backend.dto.RefreshTokenResponse;
import com.example.backend.dto.SignupRequest;

public interface AuthService {

	AuthResponse signup(SignupRequest signupRequest);

	AuthResponse login(LoginRequest loginRequest);

	RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
