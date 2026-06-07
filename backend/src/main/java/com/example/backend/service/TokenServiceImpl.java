package com.example.backend.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

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

import com.example.backend.service.interfaces.TokenService;

@Service
@SuppressWarnings("unused")
public class TokenServiceImpl implements TokenService {

	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;
	private final Duration expiration;
	private final String issuer;

	public TokenServiceImpl(
			JwtEncoder jwtEncoder,
			JwtDecoder jwtDecoder,
			@Value("${app.jwt.exp-min}") int expirationMinutes,
			@Value("${app.jwt.issuer}") String issuer) {
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
		this.expiration = Duration.ofMinutes(expirationMinutes);
		this.issuer = issuer;
	}

	@Override
	public String generateToken(Authentication authentication) {
		return encode(authentication, expiration);
	}

	@Override
	public String generateRefreshToken(Authentication authentication) {
		return encode(authentication, expiration.multipliedBy(2));
	}

	@Override
	public Authentication getAuthentication(String token) {
		try {
			Jwt jwt = jwtDecoder.decode(token);
			String username = jwt.getSubject();
			String scope = jwt.getClaimAsString("scope");

			Collection<GrantedAuthority> authorities = (scope != null && !scope.isEmpty())
					? Arrays.stream(scope.split(" "))
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList())
					: Collections.emptyList();

			return new UsernamePasswordAuthenticationToken(username, null, authorities);
		} catch (Exception e) {
			return null;
		}
	}

	private String encode(Authentication authentication, Duration duration) {
		Instant now = Instant.now();
		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer(issuer)
				.issuedAt(now)
				.expiresAt(now.plus(duration))
				.subject(authentication.getName())
				.claim("scope", scope)
				.build();

		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).type("JWT").build();
		return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}
}
