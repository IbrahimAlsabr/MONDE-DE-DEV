package com.example.backend.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RefreshTokenRequest;
import com.example.backend.dto.RefreshTokenResponse;
import com.example.backend.dto.SignupRequest;
import com.example.backend.dto.UserResponse;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.service.interfaces.AuthService;
import com.example.backend.service.interfaces.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	@Override
	@Transactional
	public AuthResponse signup(SignupRequest signupRequest) {
		String email = normalize(signupRequest.getEmail());
		String username = normalize(signupRequest.getUsername());

		if (userRepository.existsByEmail(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email address is already registered");
		}
		if (userRepository.existsByUsername(username)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
		}

		try {
			User user = new User();
			user.setEmail(email);
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			userRepository.save(user);

			return login(new LoginRequest(email, signupRequest.getPassword()));

		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email or username already exists", e);
		}
	}

	@Override
	public AuthResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						normalize(loginRequest.getIdentifier()),
						loginRequest.getPassword()));

		if (!authentication.isAuthenticated()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
		}

		Object principal = authentication.getPrincipal();

		if (!(principal instanceof CustomUserDetails customUserDetails)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
		}

		User user = customUserDetails.getUser();
		String accessToken = tokenService.generateToken(authentication);
		String refreshToken = tokenService.generateRefreshToken(authentication);

		return new AuthResponse(
				accessToken,
				refreshToken,
				new UserResponse(user.getId(), user.getEmail(), user.getUsername()));
	}

	@Override
	public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String tokenValue = refreshTokenRequest.getRefreshToken();
		if (tokenValue == null || tokenValue.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "refreshToken is required");
		}

		Authentication authentication = tokenService.getAuthentication(tokenValue);
		if (authentication == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
		}

		String accessToken = tokenService.generateToken(authentication);
		String newRefreshToken = tokenService.generateRefreshToken(authentication);
		return new RefreshTokenResponse(accessToken, newRefreshToken, "Bearer");
	}

	private static String normalize(String v) {
		return v == null ? null : v.trim();
	}
}
