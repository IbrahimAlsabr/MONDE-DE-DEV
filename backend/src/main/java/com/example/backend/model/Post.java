package com.example.backend.model;

import java.time.Instant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "topic_id", nullable = false)
	private Topic topic;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;
}