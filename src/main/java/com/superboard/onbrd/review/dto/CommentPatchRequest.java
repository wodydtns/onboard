package com.superboard.onbrd.review.dto;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class CommentPatchRequest {
	private String content;
}
