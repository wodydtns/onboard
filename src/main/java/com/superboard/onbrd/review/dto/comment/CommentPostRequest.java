package com.superboard.onbrd.review.dto.comment;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class CommentPostRequest {
	private String content;
}
