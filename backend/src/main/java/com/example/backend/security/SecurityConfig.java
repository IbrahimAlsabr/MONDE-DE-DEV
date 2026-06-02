package com.example.backend.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
			DaoAuthenticationProvider daoAuthenticationProvider,
			CorsConfigurationSource corsConfigurationSource)
			throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/test/**",
								"/api/v1/auth/**",
								"/v3/api-docs/**",
								"/swagger-ui.html",
								"/swagger-ui/**")
						.permitAll()
						.anyRequest().authenticated())
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
				.headers(headers -> headers.frameOptions(f -> f.sameOrigin()))
				.authenticationProvider(daoAuthenticationProvider)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(List.of("http://localhost:*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter granted = new JwtGrantedAuthoritiesConverter();
		granted.setAuthorityPrefix(""); // ROLE_
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(granted);
		return converter;
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(DaoAuthenticationProvider provider) {
		return new ProviderManager(provider);
	}
}