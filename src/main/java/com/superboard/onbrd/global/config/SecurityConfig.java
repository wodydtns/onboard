package com.superboard.onbrd.global.config;

import static org.springframework.http.HttpMethod.*;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		http.httpBasic()
			.disable()
			.formLogin()
			.disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(
				new JwtAuthenticationFilter(jwtTokenProvider, tokenRepository),
				UsernamePasswordAuthenticationFilter.class)
			.csrf()
			.disable()
			.headers()
			.frameOptions()
			.disable()
			.and()
			.authorizeRequests()
			.mvcMatchers("/h2/**").permitAll()
			.mvcMatchers(GET, "/api/*/boardgames/{boardgameId:[0-9]+}/reviews").permitAll()
			.mvcMatchers(GET, "/api/*/tags").permitAll()
			.mvcMatchers(DELETE, "/api/*/members/{memberId:[0-9]+}").permitAll()
			.mvcMatchers(POST, "/api/*/members/sign-up").permitAll()
			.mvcMatchers(GET,
				"/api/*/members/nickname-check", "/api/*/members/mail-check").permitAll()
			.mvcMatchers(POST, "/api/*/auth/sign-in").permitAll()
			.mvcMatchers(GET, "/api/*/auth/code").permitAll()
			.mvcMatchers(POST, "/api/*/auth/code-resending", "/api/*/auth/code-check").permitAll()
			.mvcMatchers(PATCH, "/api/*/passwords").permitAll()
			.mvcMatchers("/api/**").authenticated()
			.and()
			.authorizeRequests()
			.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**")
			.permitAll();

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
