package com.superboard.onbrd.review.dto.review;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateDto {
	private String email;
	private Long boardgameId;
	private float grade;
	private String content;
	private List<String> images;

	public static ReviewCreateDto of(String email, Long boardgameId, ReviewPostRequest request) {
		ReviewCreateDto dto = new ReviewCreateDto();
		dto.email = email;
		dto.boardgameId = boardgameId;
		dto.grade = request.getGrade();
		dto.content = request.getContent();
		dto.images = request.getImages();

		return dto;
	}
}
