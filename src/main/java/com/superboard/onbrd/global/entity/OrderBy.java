package com.superboard.onbrd.global.entity;

import static com.superboard.onbrd.inquiry.entity.QInquiry.*;
import static com.superboard.onbrd.report.entity.QReport.*;
import static com.superboard.onbrd.review.entity.QComment.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import com.querydsl.core.types.OrderSpecifier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderBy {
	INQUIRY_NEWEST("1:1_문의_최신순", new OrderSpecifier<?>[] {inquiry.id.desc()}),

	REPORT_NEWEST("신고_최신순", new OrderSpecifier<?>[] {report.id.desc()}),

	REVIEW_NEWEST("리뷰_최신순", new OrderSpecifier<?>[] {review.id.desc()}),
	REVIEW_MOST_LIKE("리뷰_좋아요순", new OrderSpecifier<?>[] {review.likeCount.desc(), review.id.desc()}),

	COMMENT_NEWEST("댓글_최신순", new OrderSpecifier<?>[] {comment.id.desc()});

	private final String desc;
	private final OrderSpecifier<?>[] orderSpecifiers;
}
