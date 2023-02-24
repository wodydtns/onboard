package com.superboard.onbrd.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityPoint {
	REVIEW_WRITING(50),
	REVIEW_WRITING_WITH_PHOTO(70),
	COMMENT_WRITING(10),
	ATTENDANCE(10),
	REVIEW_LIKED(10);

	private final int quantity;
}
