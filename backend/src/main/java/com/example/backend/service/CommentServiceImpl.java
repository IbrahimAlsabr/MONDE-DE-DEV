package com.example.backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.dto.CommentCreateRequest;
import com.example.backend.dto.CommentResponse;
import com.example.backend.dto.UserSummaryResponse;
import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.model.User;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.PostRepository;
import com.example.backend.service.interfaces.CommentService;
import com.example.backend.service.interfaces.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserService userService;

	@Override
	@Transactional
	public CommentResponse addToPost(String authenticationName, Long postId, CommentCreateRequest request) {
		User user = userService.requireCurrentUser(authenticationName);
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

		Comment comment = new Comment();
		comment.setContent(request.getContent().trim());
		comment.setPost(post);
		comment.setUser(user);

		return toResponse(commentRepository.save(comment));
	}

	@Override
	public List<CommentResponse> listForPost(String authenticationName, Long postId) {
		userService.requireCurrentUser(authenticationName);

		if (!postRepository.existsById(postId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
		}

		return commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId).stream()
				.map(this::toResponse)
				.toList();
	}

	private CommentResponse toResponse(Comment c) {
		return new CommentResponse(
				c.getId(),
				c.getContent(),
				new UserSummaryResponse(c.getUser().getId(), c.getUser().getUsername()),
				c.getCreatedAt());
	}
}
