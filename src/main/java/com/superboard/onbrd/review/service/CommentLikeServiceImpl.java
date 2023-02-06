package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.review.entity.Comment;
import com.superboard.onbrd.review.entity.CommentLike;
import com.superboard.onbrd.review.repository.CommentLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeServiceImpl implements CommentLikeService {
	private final CommentLikeRepository commentLikeRepository;
	private final MemberService memberService;
	private final CommentService commentService;

	@Override
	public void createCommentLikeOrDeleteIfExist(String email, Long commentId) {
		Comment comment = commentService.findVerifiedOneById(commentId);

		checkOwnComment(email, comment);

		Member member = memberService.findVerifiedOneByEmail(email);

		Optional<CommentLike> commentLikeOptional = commentLikeRepository.findByMemberAndComment(member, comment);
		commentLikeOptional.ifPresentOrElse(
			commentLikeRepository::delete,
			() -> commentLikeRepository.save(CommentLike.of(member, comment))
		);
	}

	private void checkOwnComment(String email, Comment comment) {
		if (comment.getWriter().getEmail().equals(email)) {
			throw new BusinessLogicException(LIKE_OWN_COMMENT_NOT_PERMITTED);
		}
	}
}
