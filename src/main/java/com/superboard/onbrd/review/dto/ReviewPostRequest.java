package com.superboard.onbrd.review.dto;

import java.util.List;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class ReviewPostRequest {
	private float grade;
	private String content;
	private List<String> images;
}
