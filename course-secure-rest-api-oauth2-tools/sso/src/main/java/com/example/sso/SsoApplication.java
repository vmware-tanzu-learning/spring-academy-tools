package com.example.sso;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class SsoApplication implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.resources().registerResource(new ClassPathResource("authz.pem"));
		hints.resources().registerResource(new ClassPathResource("authz.pub"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SsoApplication.class, args);
	}

}
