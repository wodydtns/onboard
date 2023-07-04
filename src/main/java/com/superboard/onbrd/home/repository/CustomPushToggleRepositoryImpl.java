package com.superboard.onbrd.home.repository;

import static com.superboard.onbrd.home.entity.QPushToggle.*;
import static com.superboard.onbrd.member.entity.QMember.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
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
				Projections.fields(PushToggleDto.class, pushToggle.id,pushToggle.member.id.as("memberId"), pushToggle.commentYn, pushToggle.favoriteTagYn))
			.from(pushToggle).join(pushToggle.member, member)
			.where(member.id.eq(id))
			.fetchOne();

	}

}
