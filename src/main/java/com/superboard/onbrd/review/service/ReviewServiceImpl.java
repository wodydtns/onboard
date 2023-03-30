package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.member.entity.ActivityPoint.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.service.BoardGameService;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.util.OciObjectStorageUtil;
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
	private final OciObjectStorageUtil ociObjectStorageUtil;

	@Override
	@Transactional(readOnly = true)
	public OnbrdPageResponse<AdminReviewDetail> getAdminReviews(OnbrdPageRequest params) {
		return reviewRepository.getAdminReviews(params);
	}

	@Override
	@Transactional(readOnly = true)
	public ReviewByBoardgameIdResponse getReviewsByBoardgameId(ReviewGetParameterDto params) {
		return reviewRepository.searchReviewsByBoardgameId(params);
	}

	@Override
	public Review crewateReview(ReviewCreateDto dto, List<MultipartFile> files) {
		if (!files.isEmpty()) {
			List<String> imageList = new ArrayList<>();
			for (MultipartFile multipartFile : files) {
				imageList.add(multipartFile.getOriginalFilename());
				String filePath = "review/";
				try {
					ociObjectStorageUtil.UploadObject(multipartFile, filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dto.setImages(imageList);
		}
		Member writer = memberService.findVerifiedOneByEmail(dto.getEmail());
		Boardgame boardgame = boardGameService.findVerifiedOneById(dto.getBoardgameId());

		Review created = Review.builder()
			.writer(writer)
			.boardgame(boardgame)
			.grade(dto.getGrade())
			.content(dto.getContent())
			.images(dto.getImages())
			.build();

		writer.increasePoint(REVIEW_WRITING.getPoint());
		writer.updateLevel(
			MemberLevel.getLevelCorrespondingPoint(writer.getPoint()));

		return reviewRepository.save(created);
	}

	@Override
	public Review updateReview(ReviewUpdateDto dto, List<MultipartFile> files) {
		Review updated = findVerifiedOneById(dto.getReviewId());
		if (!files.isEmpty()) {
			for (MultipartFile file : files) {
				try {
					String filePath = "review/";
					boolean isExist = ociObjectStorageUtil.getObjectOne(file.getOriginalFilename(), filePath);
					if (!isExist) {
						ociObjectStorageUtil.UploadObject(file, filePath);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		updated.updateGrade(dto.getGrade());
		updated.updateContent(dto.getContent());
		updated.updateImages(dto.getImages());

		return updated;
	}

	@Override
	public Review hideReview(Long id) {
		Review review = findVerifiedOneById(id);
		review.hide();

		return review;
	}

	@Override
	public void deleteReviewById(Long id) {
		Review deleted = findVerifiedOneById(id);
		List<String> imageList = deleted.getImages();
		if (!imageList.isEmpty()) {
			try {
				String filePath = "review/";
				ociObjectStorageUtil.deleteObject(imageList, filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	public List<ReviewHomeByFavoriteCount> selectRecommandReviewList(PageBasicEntity pageBasicEntity) {
		return reviewRepository.selectRecommandReviewList(pageBasicEntity);
	}
}
