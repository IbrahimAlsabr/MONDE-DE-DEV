package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RefreshTokenRequest;
import com.example.backend.dto.RefreshTokenResponse;
import com.example.backend.dto.SignupRequest;
import com.example.backend.service.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

	private final AuthService authService;

	// ===========================================================
	// ======================== LOGIN ============================
	// ===========================================================
	@PostMapping("/login")
	@Operation(summary = "Login", description = "Login to the system")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

		return ResponseEntity.ok(authService.login(loginRequest));
	}

	// ===========================================================
	// ==================== REGISTER USER ========================
	// ===========================================================
	@PostMapping("/signup")
	@Operation(summary = "Register", description = "Register a new user account")
	public ResponseEntity<AuthResponse> signup(
			@Valid @RequestBody SignupRequest signupRequest) {

		return ResponseEntity.ok(authService.signup(signupRequest));
	}

	// ===========================================================
	// ================== REFRESH TOKEN ==========================
	// ===========================================================
	@PostMapping("/refresh")
	@Operation(summary = "Refresh token", description = "Refresh the token")
	public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

		return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
	}
}