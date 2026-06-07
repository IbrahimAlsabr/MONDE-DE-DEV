package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.TopicResponse;
import com.example.backend.service.interfaces.SubscriptionService;
import com.example.backend.service.interfaces.TopicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topics")
@Tag(name = "Topics", description = "Topics endpoints")
public class TopicController {
	private final TopicService topicService;
	private final SubscriptionService subscriptionService;

	// ===========================================================
	// ================== LIST TOPICS ============================
	// ===========================================================
	@GetMapping
	@Operation(summary = "List topics", description = "List all topics and whether the current user is subscribed")
	public ResponseEntity<List<TopicResponse>> list(Authentication authentication) {
		return ResponseEntity.ok(topicService.listTopics(authentication.getName()));
	}

	// ===========================================================
	// ================== SUBSCRIBE ==============================
	// ===========================================================
	@PostMapping("/{topicId}/subscribe")
	@Operation(summary = "Subscribe", description = "Subscribe the current user to a topic")
	public ResponseEntity<Void> subscribe(Authentication authentication, @PathVariable Long topicId) {
		subscriptionService.subscribe(authentication.getName(), topicId);
		return ResponseEntity.noContent().build();
	}
}

