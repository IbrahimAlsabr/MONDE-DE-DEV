package com.example.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
	private Long id;
	private String email;
	private String username;
	private List<TopicSummaryResponse> subscriptions;
}

