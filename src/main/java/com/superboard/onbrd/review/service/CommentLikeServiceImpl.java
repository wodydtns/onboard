package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import com.superboard.onbrd.review.entity.Comments;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
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
		Comments comments = commentService.findVerifiedOneById(commentId);

		checkOwnComment(email, comments);

		Member member = memberService.findVerifiedOneByEmail(email);

		Optional<CommentLike> commentLikeOptional = commentLikeRepository.findByMemberAndComments(member, comments);
		commentLikeOptional.ifPresentOrElse(
			commentLikeRepository::delete,
			() -> commentLikeRepository.save(CommentLike.of(member, comments))
		);
	}

	private void checkOwnComment(String email, Comments comments) {
		if (comments.getWriter().getEmail().equals(email)) {
			throw new BusinessLogicException(LIKE_OWN_COMMENT_NOT_PERMITTED);
		}
	}
}
