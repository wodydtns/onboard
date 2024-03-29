package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.review.entity.Review;
import com.superboard.onbrd.review.entity.ReviewLike;
import com.superboard.onbrd.review.repository.ReviewLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewLikeServiceImpl implements ReviewLikeService {
	private final ReviewLikeRepository reviewLikeRepository;
	private final ReviewService reviewService;
	private final MemberService memberService;

	@Override
	public void createReviewLikeOrDeleteIfExist(String email, Long reviewId) {
		Review review = reviewService.findVerifiedOneById(reviewId);
		Member writer = review.getWriter();

		checkOwnReview(email, writer);

		Member member = memberService.findVerifiedOneByEmail(email);

		Optional<ReviewLike> reviewLikeOptional = reviewLikeRepository.findByMemberAndReview(member, review);
		reviewLikeOptional.ifPresentOrElse(
			reviewLike -> {
				reviewLikeRepository.delete(reviewLike);
				review.gainLikeOrLoseIfCanceled(true);
			},
			() -> {
				ReviewLike reviewLike = ReviewLike.of(member, review);
				reviewLikeRepository.save(reviewLike);
				review.gainLikeOrLoseIfCanceled(false);
			}
		);
	}

	@Override
	public boolean isLikedBy(String email, Long reviewId) {
		return reviewLikeRepository.existsByMember_EmailAndReview_Id(email, reviewId);
	}

	private void checkOwnReview(String email, Member writer) {
		if (writer.getEmail().equals(email)) {
			throw new BusinessLogicException(LIKE_OWN_REVIEW_NOT_PERMITTED);
		}
	}
}
