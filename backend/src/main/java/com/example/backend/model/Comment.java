package com.example.backend.model;

import java.time.Instant;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false)
	private String content;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
