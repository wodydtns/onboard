package com.superboard.onbrd.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateDto {
	private Long commentId;
	private String content;

	public static CommentUpdateDto of(Long commentId, CommentPatchRequest request) {
		CommentUpdateDto dto = new CommentUpdateDto();
		dto.commentId = commentId;
		dto.content = request.getContent();

		return dto;
	}
}
