package com.example.backend.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
public class JWTConfig {

	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Bean
	public JwtEncoder jwtEncoder() {
		var secret = jwtSecret.getBytes(StandardCharsets.UTF_8);
		var jwk = new OctetSequenceKey.Builder(secret).algorithm(JWSAlgorithm.HS256).build();
		var jwkSet = new JWKSet(jwk);
		var jwkSource = new ImmutableJWKSet<>(jwkSet);
		return new NimbusJwtEncoder(jwkSource);
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKey key = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
	}

}