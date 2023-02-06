package com.superboard.onbrd.review.dto.review;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateDto {
	private Long reviewId;
	private float grade;
	private String content;
	private List<String> images;

	public static ReviewUpdateDto of(Long reviewId, ReviewPatchRequest request) {
		ReviewUpdateDto dto = new ReviewUpdateDto();
		dto.reviewId = reviewId;
		dto.grade = request.getGrade();
		dto.content = request.getContent();
		dto.images = request.getImages();

		return dto;
	}
}
