package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.CommentCreateRequest;
import com.example.backend.dto.CommentResponse;
import com.example.backend.service.interfaces.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
@Tag(name = "Comments", description = "Comments endpoints")
public class CommentController {
	private final CommentService commentService;

	// ===========================================================
	// ================== LIST COMMENTS ==========================
	// ===========================================================
	@GetMapping
	@Operation(summary = "List comments", description = "List comments for a post (oldest to newest)")
	public ResponseEntity<List<CommentResponse>> list(Authentication authentication, @PathVariable Long postId) {
		return ResponseEntity.ok(commentService.listForPost(authentication.getName(), postId));
	}

	// ===========================================================
	// ================== ADD COMMENT ============================
	// ===========================================================
	@PostMapping
	@Operation(summary = "Add comment", description = "Add a comment to a post (author and date are set automatically)")
	public ResponseEntity<CommentResponse> add(
			Authentication authentication,
			@PathVariable Long postId,
			@Valid @RequestBody CommentCreateRequest req) {
		return ResponseEntity.ok(commentService.addToPost(authentication.getName(), postId, req));
	}
}
