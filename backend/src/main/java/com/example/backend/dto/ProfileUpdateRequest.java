package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequest {
	@Email
	@Size(max = 255)
	private String email;

	@Size(min = 3, max = 50)
	private String username;

	@Size(min = 8, max = 50)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$")
	private String password;
}
