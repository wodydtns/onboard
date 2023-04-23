package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.member.entity.ActivityPoint.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberLevel;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.review.dto.comment.CommentCreateDto;
import com.superboard.onbrd.review.dto.comment.CommentDetail;
import com.superboard.onbrd.review.dto.comment.CommentUpdateDto;
import com.superboard.onbrd.review.entity.Comment;
import com.superboard.onbrd.review.entity.Review;
import com.superboard.onbrd.review.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;
	private final MemberService memberService;
	private final ReviewService reviewService;

	@Override
	public OnbrdSliceResponse<AdminCommentDetail> getAdminComment(OnbrdSliceRequest params) {
		return commentRepository.getAdminComments(params);
	}

	@Override
	public Comment createComment(CommentCreateDto dto) {
		Member writer = memberService.findVerifiedOneByEmail(dto.getEmail());
		Review review = reviewService.findVerifiedOneById(dto.getReviewId());

		Comment created = Comment.builder()
			.writer(writer)
			.review(review)
			.content(dto.getContent())
			.build();

		writer.increasePoint(COMMENT_WRITING.getPoint());
		writer.updateLevel(
			MemberLevel.getLevelCorrespondingPoint(writer.getPoint()));

		return commentRepository.save(created);
	}

	@Override
	public Comment updateComment(CommentUpdateDto dto) {
		Comment updated = findVerifiedOneById(dto.getCommentId());
		updated.updateContent(dto.getContent());

		return updated;
	}

	@Override
	public Comment hideComment(Long id) {
		Comment comment = findVerifiedOneById(id);

		comment.hide();

		return comment;
	}

	@Override
	public void deleteCommentById(Long id) {
		Comment deleted = findVerifiedOneById(id);
		commentRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public Comment findVerifiedOneById(Long id) {
		Optional<Comment> commentOptional = commentRepository.findById(id);

		return commentOptional
			.orElseThrow(
				() -> {
					throw new BusinessLogicException(COMMENT_NOT_FOUND);
				}
			);
	}

	@Override
	public OnbrdSliceResponse<CommentDetail> getCommentsByReviewId(Long reviewId, OnbrdSliceRequest request) {
		return commentRepository.getCommentsByReviewId(reviewId, request);
	}
}
