package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.member.entity.ActivityPoint.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.service.BoardGameService;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberLevel;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewCreateDto;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;
import com.superboard.onbrd.review.dto.review.ReviewUpdateDto;
import com.superboard.onbrd.review.entity.Review;
import com.superboard.onbrd.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;
	private final MemberService memberService;
	private final BoardGameService boardGameService;

	@Override
	public ReviewByBoardgameIdResponse getReviewsByBoardgameId(ReviewGetParameterDto params) {
		return reviewRepository.searchReviewsByBoardgameId(params);
	}

	@Override
	public Review crewateReview(ReviewCreateDto dto) {
		Member writer = memberService.findVerifiedOneByEmail(dto.getEmail());
		Boardgame boardgame = boardGameService.findVerifiedOneById(dto.getBoardgameId());

		Review created = Review.builder()
			.writer(writer)
			.boardgame(boardgame)
			.grade(dto.getGrade())
			.content(dto.getContent())
			.images(dto.getImages())
			.build();

		writer.increasePoint(REVIEW_WRITING.point());
		writer.updateLevel(
			MemberLevel.getLevelCorrespondingPoint(writer.getPoint()));

		return reviewRepository.save(created);
	}

	@Override
	public Review updateReview(ReviewUpdateDto dto) {
		Review updated = findVerifiedOneById(dto.getReviewId());

		updated.updateGrade(dto.getGrade());
		updated.updateContent(dto.getContent());
		updated.updateImages(dto.getImages());

		return updated;
	}

	@Override
	public void deleteReviewById(Long id) {
		Review deleted = findVerifiedOneById(id);
		reviewRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public Review findVerifiedOneById(Long id) {
		Optional<Review> reviewOptional = reviewRepository.findById(id);

		return reviewOptional.orElseThrow(() -> {
			throw new BusinessLogicException(REVIEW_NOT_FOUND);
		});
	}

	@Override
	public Page<ReviewHomeByFavoriteCount> selectRecommandReviewList(Pageable pageable) {
		return reviewRepository.selectRecommandReviewList(pageable);
	}
}
