package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.PostResponse;
import com.example.backend.service.FeedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
@Tag(name = "Feed", description = "Feed endpoints")
public class FeedController {
	private final FeedService feedService;

	// ===========================================================
	// ================== GET FEED ===============================
	// ===========================================================
	@GetMapping
	@Operation(summary = "Get feed", description = "Get the current user's feed (posts from subscribed topics)")
	public ResponseEntity<List<PostResponse>> feed(
			Authentication authentication,
			@RequestParam(name = "sort", required = false, defaultValue = "desc") String sort) {
		return ResponseEntity.ok(feedService.getFeed(authentication.getName(), sort));
	}
}
