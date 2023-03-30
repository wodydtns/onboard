package com.superboard.onbrd.inquiry.repository;

import static com.superboard.onbrd.global.entity.OrderBy.*;
import static com.superboard.onbrd.inquiry.entity.QInquiry.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminInquiryDetail;
import com.superboard.onbrd.global.dto.OnbrdPageInfo;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.global.entity.OrderBy;
import com.superboard.onbrd.inquiry.dto.InquiryGetResponse;
import com.superboard.onbrd.inquiry.dto.InquiryMyListResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomInquiryRepositoryImpl implements CustomInquiryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public InquiryGetResponse getInquiryResponse(Long id) {

		return queryFactory
			.select(Projections.fields(InquiryGetResponse.class,
				inquiry.id,
				inquiry.title,
				inquiry.content,
				inquiry.isAnswered,
				inquiry.answer,
				inquiry.answeredAt,
				inquiry.admin.nickname.as("adminNickname"),
				inquiry.createdAt
			))
			.from(inquiry)
			.where(inquiry.id.eq(id))
			.fetchOne();
	}

	@Override
	public InquiryMyListResponse getMyInquiries(String email) {
		List<InquiryMyListResponse.InquiryCard> MyInquiries = queryFactory
			.select(Projections.fields(InquiryMyListResponse.InquiryCard.class,
				inquiry.id,
				inquiry.title,
				inquiry.content,
				inquiry.isAnswered,
				inquiry.createdAt
			))
			.from(inquiry)
			.where(inquiry.member.email.eq(email))
			.orderBy(inquiry.id.desc())
			.fetch();

		return InquiryMyListResponse.from(MyInquiries);
	}

	@Override
	public OnbrdPageResponse<AdminInquiryDetail> getAdminInquiries(OnbrdPageRequest params) {
		OrderBy orderBy = INQUIRY_NEWEST;

		List<AdminInquiryDetail> content = queryFactory
			.select(Projections.fields(AdminInquiryDetail.class,
				inquiry.id,
				inquiry.title,
				inquiry.content,
				inquiry.member.id.as("memberId"),
				inquiry.member.nickname,
				inquiry.createdAt,
				inquiry.isAnswered,
				inquiry.answer
			))
			.from(inquiry)
			.orderBy(orderBy.getOrderSpecifiers())
			.offset(params.getOffset())
			.limit(params.getPageSize())
			.fetch();

		long totalElements = queryFactory
			.select(inquiry.count())
			.from(inquiry)
			.fetchFirst();

		OnbrdPageInfo pageInfo = OnbrdPageInfo.of(params, content, totalElements, orderBy);

		return new OnbrdPageResponse<>(pageInfo, content);
	}
}
