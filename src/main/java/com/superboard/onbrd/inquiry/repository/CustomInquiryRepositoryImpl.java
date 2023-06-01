package com.superboard.onbrd.inquiry.repository;

import static com.superboard.onbrd.global.entity.OrderBy.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.inquiry.entity.QInquiry.*;
import static com.superboard.onbrd.member.entity.QMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminInquiryDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceInfo;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.inquiry.dto.InquiryGetQuery;
import com.superboard.onbrd.inquiry.dto.InquiryGetResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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
				inquiry.adminEmail,
				inquiry.createdAt
			))
			.from(inquiry)
			.where(inquiry.id.eq(id))
			.fetchOne();
	}

	@Override
	public OnbrdSliceResponse<InquiryGetResponse> getMyInquiries(InquiryGetQuery query) {
		log.error(query.getEmail());

		Long memberId = queryFactory
			.select(member.id)
			.from(member)
			.where(member.email.eq(query.getEmail()))
			.fetchOne();

		log.error("" + memberId);

		List<InquiryGetResponse> content = queryFactory
			.select(Projections.fields(InquiryGetResponse.class,
				inquiry.id,
				inquiry.title,
				inquiry.content,
				inquiry.isAnswered,
				inquiry.answer,
				inquiry.adminEmail,
				inquiry.answeredAt,
				inquiry.createdAt
			))
			.from(inquiry)
			.where(inquiry.member.id.eq(memberId))
			.orderBy(inquiry.id.desc())
			.offset(query.getOffset())
			.limit(query.getLimit() + 1)
			.fetch();

		log.error("" + content.size());

		OnbrdSliceInfo pageInfo = getSliceInfo(content, query.getLimit());

		return new OnbrdSliceResponse<>(pageInfo, content);
	}

	@Override
	public OnbrdSliceResponse<AdminInquiryDetail> getAdminInquiries(OnbrdSliceRequest params) {
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
			.orderBy(INQUIRY_NEWEST.getOrderSpecifiers())
			.offset(params.getOffset())
			.limit(params.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, params.getLimit());

		return new OnbrdSliceResponse<>(pageInfo, content);
	}
}
