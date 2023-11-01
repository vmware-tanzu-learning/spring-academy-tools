package com.example.sso;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return (context) -> context.getClaims().subject("sarah1");
	}

	@Bean
	JWKSource<SecurityContext> jwks(@Value("classpath:authz.pub") RSAPublicKey pub,
									@Value("classpath:authz.pem") RSAPrivateKey key) throws Exception {
		return new ImmutableJWKSet<>(new JWKSet(
				new RSAKey.Builder(pub).privateKey(key).keyIDFromThumbprint().build()));
	}
}
