package com.example.backend.model;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@OneToMany(mappedBy = "topic")
	private List<Post> posts;
}
