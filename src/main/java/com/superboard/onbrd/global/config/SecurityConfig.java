package com.superboard.onbrd.global.config;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.superboard.onbrd.auth.filter.JwtAuthenticationFilter;
import com.superboard.onbrd.auth.repository.TokenRepository;
import com.superboard.onbrd.auth.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;
	private final TokenRepository tokenRepository;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
			.formLogin().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(
				new JwtAuthenticationFilter(jwtTokenProvider, tokenRepository),
				UsernamePasswordAuthenticationFilter.class)
			.csrf().disable()
			.headers()
			.frameOptions().disable()
			.and()
			.authorizeRequests()
			.mvcMatchers("/h2/**").permitAll()
			.mvcMatchers(HttpMethod.POST, "/api/*/members/sign-up").permitAll()
			.mvcMatchers(HttpMethod.GET,
				"/api/*/members/nickname-check", "/api/*/members/mail-check").permitAll()
			.mvcMatchers(HttpMethod.POST, "/api/*/auth/sign-in").permitAll()
			.mvcMatchers(HttpMethod.GET, "/api/*/auth/code").permitAll()
			.mvcMatchers(HttpMethod.POST, "/api/*/auth/code-resending", "/api/*/auth/code-check").permitAll()
			.mvcMatchers(HttpMethod.PATCH, "/api/*/passwords").permitAll()
			.mvcMatchers("/api/**").authenticated();

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public SecureRandom secureRandom() {
		return new SecureRandom();
	}
}
