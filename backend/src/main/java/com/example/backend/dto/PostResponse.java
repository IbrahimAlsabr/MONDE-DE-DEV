package com.example.backend.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
	private Long id;
	private TopicSummaryResponse topic;
	private String title;
	private String content;
	private UserSummaryResponse author;
	private Instant createdAt;
	private List<CommentResponse> comments;
}

