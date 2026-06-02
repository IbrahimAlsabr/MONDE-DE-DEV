package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Size(min = 3, max = 50)
	private String username;

	@NotBlank
	@Size(min = 8, max = 50)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).*$")
	private String password;

}
