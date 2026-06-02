package com.example.backend.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByTopicIdOrderByCreatedAtDesc(Long topicId);

	List<Post> findAllByTopicIdInOrderByCreatedAtDesc(Collection<Long> topicIds);
	List<Post> findAllByTopicIdInOrderByCreatedAtAsc(Collection<Long> topicIds);

	List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}

