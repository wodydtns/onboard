package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.member.entity.ActivityPoint.*;

import java.util.Optional;

import com.superboard.onbrd.review.entity.Comments;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberLevel;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.review.dto.comment.CommentCreateDto;
import com.superboard.onbrd.review.dto.comment.CommentUpdateDto;
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
	public Comments createComments(CommentCreateDto dto) {
		Member writer = memberService.findVerifiedOneByEmail(dto.getEmail());
		Review review = reviewService.findVerifiedOneById(dto.getReviewId());

		Comments created = Comments.builder()
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
	public Comments updateComments(CommentUpdateDto dto) {
		Comments updated = findVerifiedOneById(dto.getCommentId());
		updated.updateContent(dto.getContent());

		return updated;
	}

	@Override
	public void deleteCommentsById(Long id) {
		Comments deleted = findVerifiedOneById(id);
		commentRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public Comments findVerifiedOneById(Long id) {
		Optional<Comments> commentOptional = commentRepository.findById(id);

		return commentOptional
			.orElseThrow(
				() -> {
					throw new BusinessLogicException(COMMENT_NOT_FOUND);
				}
			);
	}
}
