package com.example.backend.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.TopicResponse;
import com.example.backend.model.Subscription;
import com.example.backend.model.User;
import com.example.backend.repository.SubscriptionRepository;
import com.example.backend.repository.TopicRepository;
import com.example.backend.service.interfaces.TopicService;
import com.example.backend.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

	private final TopicRepository topicRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final UserService userService;

	@Override
	public List<TopicResponse> listTopics(String authenticationName) {
		User user = userService.requireCurrentUser(authenticationName);

		Set<Long> subscribedTopicIds = subscriptionRepository.findAllByUserId(user.getId()).stream()
				.map(Subscription::getTopic)
				.map(t -> t.getId())
				.collect(Collectors.toSet());

		return topicRepository.findAll().stream()
				.map(t -> new TopicResponse(t.getId(), t.getName(), t.getDescription(), subscribedTopicIds.contains(t.getId())))
				.toList();
	}
}
