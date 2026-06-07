package com.example.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.model.Subscription;
import com.example.backend.model.Topic;
import com.example.backend.model.User;
import com.example.backend.repository.SubscriptionRepository;
import com.example.backend.repository.TopicRepository;
import com.example.backend.service.interfaces.SubscriptionService;
import com.example.backend.service.interfaces.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository subscriptionRepository;
	private final TopicRepository topicRepository;
	private final UserService userService;

	@Override
	@Transactional
	public void subscribe(String authenticationName, Long topicId) {
		User user = userService.requireCurrentUser(authenticationName);
		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));

		if (subscriptionRepository.existsByUserIdAndTopicId(user.getId(), topic.getId())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Already subscribed");
		}

		Subscription sub = new Subscription();
		sub.setUser(user);
		sub.setTopic(topic);
		subscriptionRepository.save(sub);
	}

	@Override
	@Transactional
	public void unsubscribe(String authenticationName, Long topicId) {
		User user = userService.requireCurrentUser(authenticationName);
		Subscription sub = subscriptionRepository.findByUserIdAndTopicId(user.getId(), topicId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));
		subscriptionRepository.delete(sub);
	}
}
