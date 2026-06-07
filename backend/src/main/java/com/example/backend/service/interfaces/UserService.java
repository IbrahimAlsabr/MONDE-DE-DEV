package com.example.backend.service.interfaces;

import com.example.backend.dto.ProfileResponse;
import com.example.backend.dto.ProfileUpdateRequest;
import com.example.backend.model.User;

public interface UserService {

	/** Résout l'utilisateur connecté à partir du nom du principal JWT. */
	User requireCurrentUser(String authenticationName);

	ProfileResponse getMe(String authenticationName);

	ProfileResponse updateMe(String authenticationName, ProfileUpdateRequest request);
}
