package com.superboard.onbrd.notification.repository;

import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.notification.entity.QNotification.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.global.dto.OnbrdSliceInfo;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.notification.dto.query.NotificationGetQuery;
import com.superboard.onbrd.notification.dto.response.NotificationGetResponse;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public OnbrdSliceResponse<NotificationGetResponse> getNotifications(NotificationGetQuery query) {
		List<NotificationGetResponse> content = queryFactory
			.select(Projections.fields(NotificationGetResponse.class,
				notification.id,
				notification.notificationType,
				notification.payload,
				notification.isChecked,
				notification.pushedAt
			))
			.from(notification)
			.where(notification.receiver.email.eq(query.getEmail()))
			.orderBy(notification.id.desc())
			.offset(query.getOffset())
			.limit(query.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, query.getLimit());

		return new OnbrdSliceResponse<>(pageInfo, content);
	}
}
