package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.PostCreateRequest;
import com.example.backend.dto.PostResponse;
import com.example.backend.service.interfaces.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "Posts", description = "Posts endpoints")
public class PostController {
	private final PostService postService;

	// ===========================================================
	// ================== CREATE POST ============================
	// ===========================================================
	@PostMapping
	@Operation(summary = "Create post", description = "Create a new post (author and date are set automatically)")
	public ResponseEntity<PostResponse> create(Authentication authentication, @Valid @RequestBody PostCreateRequest req) {
		return ResponseEntity.ok(postService.create(authentication.getName(), req));
	}

	// ===========================================================
	// ================== GET POST ===============================
	// ===========================================================
	@GetMapping("/{id}")
	@Operation(summary = "Get post", description = "Get a post with its comments")
	public ResponseEntity<PostResponse> get(Authentication authentication, @PathVariable Long id) {
		return ResponseEntity.ok(postService.getById(authentication.getName(), id));
	}
}

