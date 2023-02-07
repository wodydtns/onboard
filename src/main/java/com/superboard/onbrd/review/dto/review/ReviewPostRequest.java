package com.superboard.onbrd.review.dto.review;

import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class ReviewPostRequest {
	@Schema(example = "3.5")
	private float grade;
	private String content;
	private List<String> images;
}
