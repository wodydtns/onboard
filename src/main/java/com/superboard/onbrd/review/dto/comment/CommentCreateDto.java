package com.superboard.onbrd.review.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateDto {
	private String email;
	private Long reviewId;
	private String content;

	public static CommentCreateDto of(String email, Long reviewId, CommentPostRequest request) {
		CommentCreateDto dto = new CommentCreateDto();
		dto.email = email;
		dto.reviewId = reviewId;
		dto.content = request.getContent();

		return dto;
	}
}
