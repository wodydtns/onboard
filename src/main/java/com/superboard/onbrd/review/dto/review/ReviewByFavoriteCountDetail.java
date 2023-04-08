package com.superboard.onbrd.review.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewByFavoriteCountDetail {
	private Long id;
	private String image;
	private String content;
	private String writer;
	private String level;
	private String title;
	private long likeCount;
}
