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

import com.superboard.onbrd.auth.controller.UnauthorizedExceptionEntryPoint;
import com.superboard.onbrd.auth.filter.JwtAuthenticationFilter;
import com.superboard.onbrd.auth.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;

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
				new JwtAuthenticationFilter(jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class)
			.csrf()
			.disable()
			.headers()
			.frameOptions()
			.disable()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(unauthorizedExceptionEntryPoint())
			.and()
			.authorizeRequests()
			.mvcMatchers("/api/**/admin/**").hasRole("ADMIN")
			.mvcMatchers("/api/**/members/{memberId:[0-9]+}/authority").permitAll()
			// .mvcMatchers("/api/*/admin/**").authenticated()
			.mvcMatchers("/h2/**").permitAll()
			.mvcMatchers("/api/*/auth/token-reissue").permitAll()
			.mvcMatchers(GET, "/api/*/boardgames/{boardgameId:[0-9]+}/reviews").permitAll()
			.mvcMatchers(GET, "/api/v1/boardgames/{boardgameId:[0-9]+}/reviews/{reviewId:[0-9]+}/comments").permitAll()
			.mvcMatchers(GET, "/api/*/tags").permitAll()
			.mvcMatchers(DELETE, "/api/*/members/{memberId:[0-9]+}").permitAll()
			.mvcMatchers(POST, "/api/*/members/sign-up").permitAll()
			.mvcMatchers(GET,
				"/api/*/members/nickname-check", "/api/*/members/mail-check").permitAll()
			.mvcMatchers(POST, "/api/*/auth/sign-in").permitAll()
			.mvcMatchers(GET, "/api/*/auth/code").permitAll()
			.mvcMatchers(POST, "/api/*/auth/code-resending", "/api/*/auth/code-check").permitAll()
			.mvcMatchers(PATCH, "/api/*/passwords").permitAll()
			.mvcMatchers(GET, "/api/*/passwords/code").permitAll()
			.mvcMatchers(POST, "api/*/passwords/code-check").permitAll()
			.mvcMatchers(GET, "api/*/boardgames/curation").permitAll()
			.mvcMatchers(GET, "/api/*/members/{memberId:[0-9]+}").permitAll()
			.mvcMatchers(GET, "/api/*/boardgames/{boardgameId:[0-9]+}").permitAll()
			.mvcMatchers(GET, "/api/*/boardgames/curation").permitAll()
			.mvcMatchers(GET, "/api/*/boardgames/searchBoardgameList").permitAll()
			.mvcMatchers(GET, "/api/*/boardgames/top10").permitAll()
			.mvcMatchers(GET, "/api/*/reviews/**").permitAll()
			.and()
			.authorizeRequests()
			.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**")
			.permitAll()
			.anyRequest()
			.authenticated();

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

	@Bean
	public UnauthorizedExceptionEntryPoint unauthorizedExceptionEntryPoint() {
		return new UnauthorizedExceptionEntryPoint();
	}
}
