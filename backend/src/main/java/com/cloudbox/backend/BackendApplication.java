package com.cloudbox.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional
				.of(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
