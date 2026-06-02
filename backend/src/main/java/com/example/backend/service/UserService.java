package com.example.backend.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.backend.dto.ProfileResponse;
import com.example.backend.dto.ProfileUpdateRequest;
import com.example.backend.dto.TopicSummaryResponse;
import com.example.backend.model.Subscription;
import com.example.backend.model.User;
import com.example.backend.repository.SubscriptionRepository;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final PasswordEncoder passwordEncoder;

	public User requireCurrentUser(String authenticationName) {
		if (authenticationName == null || authenticationName.isBlank()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
		}

		return userRepository.findByEmail(authenticationName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
	}

	public ProfileResponse getMe(String authenticationName) {
		User user = requireCurrentUser(authenticationName);
		List<TopicSummaryResponse> subs = subscriptionRepository.findAllByUserId(user.getId()).stream()
				.map(Subscription::getTopic)
				.map(t -> new TopicSummaryResponse(t.getId(), t.getName(), t.getDescription()))
				.toList();

		return new ProfileResponse(user.getId(), user.getEmail(), user.getUsername(), subs);
	}

	@Transactional
	public ProfileResponse updateMe(String authenticationName, ProfileUpdateRequest request) {
		User user = requireCurrentUser(authenticationName);

		if (request.getEmail() != null && !request.getEmail().isBlank()) {
			String email = request.getEmail().trim();
			if (!email.equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(email)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Email address is already registered");
			}
			user.setEmail(email);
		}

		if (request.getUsername() != null && !request.getUsername().isBlank()) {
			String username = request.getUsername().trim();
			if (!username.equalsIgnoreCase(user.getUsername()) && userRepository.existsByUsername(username)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
			}
			user.setUsername(username);
		}

		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		userRepository.save(user);
		return getMe(user.getEmail());
	}
}
