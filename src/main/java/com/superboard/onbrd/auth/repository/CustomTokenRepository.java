package com.superboard.onbrd.auth.repository;

import java.util.Optional;

import com.superboard.onbrd.auth.entity.Token;

public interface CustomTokenRepository {
	Token findByEmail(String email);

	Optional<String> findSignOutAccessTokenByEmail(String email);
}
