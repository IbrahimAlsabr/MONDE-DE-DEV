package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
	@NotNull
	private Long topicId;

	@NotBlank
	@Size(max = 150)
	private String title;

	@NotBlank
	@Size(max = 5000)
	private String content;
}

