package com.superboard.onbrd.auth.repository;

import static com.superboard.onbrd.auth.entity.QToken.*;
import static com.superboard.onbrd.member.entity.QMember.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.auth.entity.Token;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomTokenRepositoryImpl implements CustomTokenRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Token findByEmail(String email) {
		return queryFactory
			.select(token)
			.from(token)
			.join(token.member, member)
			.where(member.email.eq(email))
			.fetchFirst();
	}

	@Override
	public Optional<String> findSignOutAccessTokenByEmail(String email) {

		return Optional.ofNullable(queryFactory.select(token.signOutAccessToken)
			.from(token)
			.join(token.member, member)
			.where(member.email.eq(email))
			.fetchFirst());
	}
}
