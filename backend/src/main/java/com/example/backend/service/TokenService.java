package com.example.backend.service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;
	private final Duration expiration;
	private final String issuer;

	public TokenService(
			JwtEncoder jwtEncoder,
			JwtDecoder jwtDecoder,
			@Value("${app.jwt.exp-min}") int expirationMinutes,
			@Value("${app.jwt.issuer}") String issuer) {
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
		this.expiration = Duration.ofMinutes(expirationMinutes);
		this.issuer = issuer;
	}

	public String generateToken(Authentication auth) {
		Instant now = Instant.now();
		String scope = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer(issuer)
				.issuedAt(now)
				.expiresAt(now.plus(expiration))
				.subject(auth.getName())
				.claim("scope", scope)
				.build();

		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).type("JWT").build();

		return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}

	public String generateRefreshToken(Authentication auth) {
		Instant now = Instant.now();
		String scope = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer(issuer)
				.issuedAt(now)
				.expiresAt(now.plus(expiration.multipliedBy(2)))
				.subject(auth.getName())
				.claim("scope", scope)
				.build();

		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).type("JWT").build();

		return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}

	public Authentication getAuthentication(String token) {
		try {
			Jwt jwt = jwtDecoder.decode(token);
			String username = jwt.getSubject();
			String scope = jwt.getClaimAsString("scope");

			Collection<GrantedAuthority> authorities;
			if (scope != null && !scope.isEmpty()) {
				authorities = Arrays.stream(scope.split(" "))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
			} else {
				authorities = java.util.Collections.emptyList();
			}

			return new UsernamePasswordAuthenticationToken(username, null, authorities);
		} catch (Exception e) {
			// Return null if token is invalid or expired
			return null;
		}
	}

	public String generateInvitationToken(String normalizedEmail) {
		String combined = UUID.randomUUID() + ":" + normalizedEmail;
		return Base64.getUrlEncoder().withoutPadding()
				.encodeToString(combined.getBytes(StandardCharsets.UTF_8));
	}

	public String extractEmailFromInvitationToken(String token) {
		try {
			String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
			int separatorIndex = decoded.indexOf(":");
			if (separatorIndex == -1 || separatorIndex == decoded.length() - 1) {
				return null;
			}
			return decoded.substring(separatorIndex + 1);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

}