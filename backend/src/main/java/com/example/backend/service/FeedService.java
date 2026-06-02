package com.example.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.dto.PostResponse;
import com.example.backend.dto.TopicSummaryResponse;
import com.example.backend.dto.UserSummaryResponse;
import com.example.backend.model.Post;
import com.example.backend.model.Subscription;
import com.example.backend.model.User;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {
	private final SubscriptionRepository subscriptionRepository;
	private final PostRepository postRepository;
	private final UserService userService;

	public List<PostResponse> getFeed(String authenticationName, String sort) {
		User user = userService.requireCurrentUser(authenticationName);
		List<Long> topicIds = subscriptionRepository.findAllByUserId(user.getId()).stream()
				.map(Subscription::getTopic)
				.map(t -> t.getId())
				.toList();

		if (topicIds.isEmpty()) {
			return List.of();
		}

		List<Post> posts;
		if ("asc".equalsIgnoreCase(sort)) {
			posts = postRepository.findAllByTopicIdInOrderByCreatedAtAsc(topicIds);
		} else {
			posts = postRepository.findAllByTopicIdInOrderByCreatedAtDesc(topicIds);
		}

		return posts.stream()
				.map(this::toResponse)
				.toList();
	}

	private PostResponse toResponse(Post post) {
		return new PostResponse(
				post.getId(),
				new TopicSummaryResponse(post.getTopic().getId(), post.getTopic().getName(), post.getTopic().getDescription()),
				post.getTitle(),
				post.getContent(),
				new UserSummaryResponse(post.getUser().getId(), post.getUser().getUsername()),
				post.getCreatedAt(),
				List.of());
	}
}

