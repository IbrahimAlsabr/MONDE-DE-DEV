package com.example.backend.service.interfaces;

import java.util.List;

import com.example.backend.dto.PostResponse;

public interface FeedService {

	List<PostResponse> getFeed(String authenticationName, String sort);
}
