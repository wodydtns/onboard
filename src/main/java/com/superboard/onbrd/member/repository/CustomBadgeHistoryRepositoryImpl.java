package com.superboard.onbrd.member.repository;

import static com.superboard.onbrd.member.entity.QBadgeHistory.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.member.entity.BadgeHistory;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomBadgeHistoryRepositoryImpl implements CustomBadgeHistoryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public BadgeHistory findLastBadgeHistory(String email) {

		return queryFactory
			.select(badgeHistory)
			.from(badgeHistory)
			.where(badgeHistory.member.email.eq(email))
			.orderBy(badgeHistory.id.desc())
			.fetchFirst();
	}

	@Override
	public Optional<BadgeHistory> findLastCheckedBadgeHistory(String email) {

		return Optional.ofNullable(
			queryFactory
				.select(badgeHistory)
				.from(badgeHistory)
				.where(badgeHistory.member.email.eq(email), badgeHistory.isChecked.isTrue())
				.orderBy(badgeHistory.id.desc())
				.fetchFirst());
	}
}
