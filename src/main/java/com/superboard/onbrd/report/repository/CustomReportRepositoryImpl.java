package com.superboard.onbrd.report.repository;

import static com.superboard.onbrd.global.entity.OrderBy.*;
import static com.superboard.onbrd.report.entity.QReport.*;
import static com.superboard.onbrd.report.entity.ReportType.*;
import static com.superboard.onbrd.review.entity.QComment.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminReportDetail;
import com.superboard.onbrd.global.dto.OnbrdPageInfo;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.global.entity.OrderBy;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomReportRepositoryImpl implements CustomReportRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public OnbrdPageResponse<AdminReportDetail> getAdminReports(OnbrdPageRequest params) {
		OrderBy orderBy = REPORT_NEWEST;

		List<AdminReportDetail> content = queryFactory
			.select(Projections.fields(AdminReportDetail.class,
				report.id,
				report.type,
				report.postId,
				report.whistleblower.id.as("whistleblowerId"),
				report.whistleblower.nickname.as("whistleblowerNickname"),
				report.isResolved,
				report.createdAt.as("reportedAt")
			))
			.from(report)
			.orderBy(orderBy.getOrderSpecifiers())
			.offset(params.getOffset())
			.limit(params.getPageSize())
			.fetch();

		for (AdminReportDetail reportDetail : content) {
			if (reportDetail.getType() == REVIEW) {
				Tuple result = queryFactory
					.select(
						review.content,
						review.isHidden,
						review.createdAt,
						review.writer.id,
						review.writer.nickname
					)
					.from(review)
					.where(review.id.eq(reportDetail.getPostId()))
					.fetchFirst();

				reportDetail.setContent(result.get(review.content));
				reportDetail.setIsHidden(result.get(review.isHidden));
				reportDetail.setCreatedAt(result.get(review.createdAt));
				reportDetail.setWriterId(result.get(review.writer.id));
				reportDetail.setWriterNickname(result.get(review.writer.nickname));

				continue;
			}

			Tuple result = queryFactory
				.select(
					comment.content,
					comment.isHidden,
					comment.createdAt,
					comment.writer.id,
					comment.writer.nickname
				)
				.from(comment)
				.where(comment.id.eq(reportDetail.getPostId()))
				.fetchFirst();

			reportDetail.setContent(result.get(comment.content));
			reportDetail.setIsHidden(result.get(comment.isHidden));
			reportDetail.setCreatedAt(result.get(comment.createdAt));
			reportDetail.setWriterId(result.get(comment.writer.id));
			reportDetail.setWriterNickname(result.get(comment.writer.nickname));
		}

		Long totalElements = queryFactory
			.select(report.count())
			.from(report)
			.fetchFirst();

		OnbrdPageInfo pageInfo = OnbrdPageInfo.of(params, content, totalElements, orderBy);

		return new OnbrdPageResponse<>(pageInfo, content);
	}
}
