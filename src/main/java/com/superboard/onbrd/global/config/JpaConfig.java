package com.superboard.onbrd.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.superboard.onbrd")
@EnableJpaAuditing
public class JpaConfig {
	
}
