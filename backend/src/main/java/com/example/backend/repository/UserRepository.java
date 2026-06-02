package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmailOrUsername(String email, String username);

	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
}
