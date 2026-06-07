package com.example.backend.service.interfaces;

import com.example.backend.dto.PostCreateRequest;
import com.example.backend.dto.PostResponse;

public interface PostService {

	PostResponse create(String authenticationName, PostCreateRequest request);

	PostResponse getById(String authenticationName, Long id);
}
