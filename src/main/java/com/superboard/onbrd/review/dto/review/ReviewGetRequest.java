package com.superboard.onbrd.review.dto.review;

import com.superboard.onbrd.global.entity.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewGetRequest {
	private long offset;
	private int limit;
	private OrderBy orderBy;
}
