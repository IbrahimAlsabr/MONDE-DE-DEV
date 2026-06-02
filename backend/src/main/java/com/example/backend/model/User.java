package com.example.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Subscription> subscriptions;

}
