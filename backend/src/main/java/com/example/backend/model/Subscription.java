package com.example.backend.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;

@Entity
@Table(name = "subscriptions", uniqueConstraints = {
		@UniqueConstraint(name = "uk_subscription_user_topic", columnNames = { "user_id", "topic_id" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "topic_id", nullable = false)
	private Topic topic;
}
