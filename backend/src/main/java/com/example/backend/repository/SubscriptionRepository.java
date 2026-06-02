package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	boolean existsByUserIdAndTopicId(Long userId, Long topicId);

	Optional<Subscription> findByUserIdAndTopicId(Long userId, Long topicId);

	List<Subscription> findAllByUserId(Long userId);
}

