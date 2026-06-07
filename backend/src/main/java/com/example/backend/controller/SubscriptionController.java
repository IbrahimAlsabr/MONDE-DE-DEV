package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.service.interfaces.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions", description = "Subscription endpoints")
public class SubscriptionController {
	private final SubscriptionService subscriptionService;

	// ===========================================================
	// ================== UNSUBSCRIBE ============================
	// ===========================================================
	@DeleteMapping("/{topicId}")
	@Operation(summary = "Unsubscribe", description = "Unsubscribe the current user from a topic")
	public ResponseEntity<Void> unsubscribe(Authentication authentication, @PathVariable Long topicId) {
		subscriptionService.unsubscribe(authentication.getName(), topicId);
		return ResponseEntity.noContent().build();
	}
}

