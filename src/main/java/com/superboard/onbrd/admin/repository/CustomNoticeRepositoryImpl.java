package com.superboard.onbrd.admin.repository;

import static com.superboard.onbrd.admin.entity.QNotice.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.global.dto.OnbrdSliceInfo;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.notice.dto.NoticeDetail;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class CustomNoticeRepositoryImpl implements CustomNoticeRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public OnbrdSliceResponse<NoticeDetail> getNotices(OnbrdSliceRequest request) {
		List<NoticeDetail> content = queryFactory
			.select(Projections.fields(NoticeDetail.class,
				notice.id,
				notice.title,
				notice.content,
				notice.admin.nickname.as("admin")
			))
			.from(notice)
			.orderBy(notice.id.desc())
			.offset(request.getOffset())
			.limit(request.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, request.getLimit());

		return new OnbrdSliceResponse<>(pageInfo, content);
	}
}
