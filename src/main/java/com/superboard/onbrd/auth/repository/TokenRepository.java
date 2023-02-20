package com.superboard.onbrd.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.auth.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long>, CustomTokenRepository {
	Optional<Token> findByRefreshToken(String refreshToken);
}
