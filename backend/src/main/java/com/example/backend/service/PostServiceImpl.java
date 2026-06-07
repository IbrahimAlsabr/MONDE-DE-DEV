package com.example.backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.dto.CommentResponse;
import com.example.backend.dto.PostCreateRequest;
import com.example.backend.dto.PostResponse;
import com.example.backend.dto.TopicSummaryResponse;
import com.example.backend.dto.UserSummaryResponse;
import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.model.Topic;
import com.example.backend.model.User;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.TopicRepository;
import com.example.backend.service.interfaces.PostService;
import com.example.backend.service.interfaces.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final TopicRepository topicRepository;
	private final CommentRepository commentRepository;
	private final UserService userService;

	@Override
	@Transactional
	public PostResponse create(String authenticationName, PostCreateRequest request) {
		User user = userService.requireCurrentUser(authenticationName);
		Topic topic = topicRepository.findById(request.getTopicId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));

		Post post = new Post();
		post.setTitle(request.getTitle().trim());
		post.setContent(request.getContent().trim());
		post.setUser(user);
		post.setTopic(topic);

		return toResponse(postRepository.save(post), List.of());
	}

	@Override
	public PostResponse getById(String authenticationName, Long id) {
		userService.requireCurrentUser(authenticationName);

		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

		List<CommentResponse> comments = commentRepository
				.findAllByPostIdOrderByCreatedAtAsc(post.getId()).stream()
				.map(this::toCommentResponse)
				.toList();

		return toResponse(post, comments);
	}

	private PostResponse toResponse(Post post, List<CommentResponse> comments) {
		return new PostResponse(
				post.getId(),
				new TopicSummaryResponse(post.getTopic().getId(), post.getTopic().getName(), post.getTopic().getDescription()),
				post.getTitle(),
				post.getContent(),
				new UserSummaryResponse(post.getUser().getId(), post.getUser().getUsername()),
				post.getCreatedAt(),
				comments);
	}

	private CommentResponse toCommentResponse(Comment c) {
		return new CommentResponse(
				c.getId(),
				c.getContent(),
				new UserSummaryResponse(c.getUser().getId(), c.getUser().getUsername()),
				c.getCreatedAt());
	}
}
