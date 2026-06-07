package com.example.backend.service.interfaces;

import java.util.List;

import com.example.backend.dto.CommentCreateRequest;
import com.example.backend.dto.CommentResponse;

public interface CommentService {

	CommentResponse addToPost(String authenticationName, Long postId, CommentCreateRequest request);

	List<CommentResponse> listForPost(String authenticationName, Long postId);
}
