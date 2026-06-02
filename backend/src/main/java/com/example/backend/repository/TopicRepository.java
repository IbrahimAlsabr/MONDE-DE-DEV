package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
	Optional<Topic> findByNameIgnoreCase(String name);

	boolean existsByNameIgnoreCase(String name);
}

