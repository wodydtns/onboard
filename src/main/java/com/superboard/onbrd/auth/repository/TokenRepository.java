package com.superboard.onbrd.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.superboard.onbrd.auth.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>, CustomTokenRepository {
	Optional<Token> findByMemberId(Long memberId);
}
