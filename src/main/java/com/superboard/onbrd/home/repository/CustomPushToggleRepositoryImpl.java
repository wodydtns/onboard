package com.superboard.onbrd.home.repository;

import static com.superboard.onbrd.home.entity.QPushToggle.*;
import static com.superboard.onbrd.member.entity.QMember.*;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.home.dto.PushToggleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomPushToggleRepositoryImpl implements CustomPushToggleRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public PushToggleDto getPushToggle(long id) {
		return queryFactory.select(
				Projections.fields(PushToggleDto.class, pushToggle.id, pushToggle.commentYn, pushToggle.favoriteTagYn))
			.from(pushToggle).join(pushToggle.member, member)
			.where(member.id.eq(id))
			.fetchOne();
	}

	@Override
	public Long updatePushToggle(PushToggleDto dto) {
		return queryFactory.update(pushToggle)
			.set(pushToggle.commentYn, dto.getCommentYn())
			.set(pushToggle.favoriteTagYn, dto.getFavoriteTagYn())
			.where(pushToggle.member.id.eq(dto.getMemberId()))
			.execute();

	}

}
