package com.example.backend.service.interfaces;

import java.util.List;

import com.example.backend.dto.TopicResponse;

public interface TopicService {

	List<TopicResponse> listTopics(String authenticationName);
}
