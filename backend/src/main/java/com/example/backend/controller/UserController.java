package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.dto.ProfileResponse;
import com.example.backend.dto.ProfileUpdateRequest;
import com.example.backend.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/me")
@Tag(name = "Profile", description = "Profile endpoints")
public class UserController {
	private final UserService userService;

	// ===========================================================
	// ======================= GET PROFILE =======================
	// ===========================================================
	@GetMapping
	@Operation(summary = "Get profile", description = "Get current user's profile (email, username, subscriptions)")
	public ResponseEntity<ProfileResponse> me(Authentication authentication) {
		return ResponseEntity.ok(userService.getMe(authentication.getName()));
	}

	// ===========================================================
	// ====================== UPDATE PROFILE =====================
	// ===========================================================
	@PutMapping
	@Operation(summary = "Update profile", description = "Update current user's profile (email, username, password)")
	public ResponseEntity<ProfileResponse> update(Authentication authentication, @Valid @RequestBody ProfileUpdateRequest req) {
		return ResponseEntity.ok(userService.updateMe(authentication.getName(), req));
	}
}

