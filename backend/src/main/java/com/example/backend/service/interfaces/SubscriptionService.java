package com.example.backend.service.interfaces;

public interface SubscriptionService {

	void subscribe(String authenticationName, Long topicId);

	void unsubscribe(String authenticationName, Long topicId);
}
