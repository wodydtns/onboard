package com.superboard.onbrd.review.dto.review;

import com.superboard.onbrd.global.entity.OrderBy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewGetRequest {
	private long offset;
	private int limit;
	@Schema(description = "REVIEW_NEWEST: 리뷰 최신순, REVIEW_MOST_LIKE: 리뷰 좋아요 많은순",
		allowableValues = {"REVIEW_NEWEST", "REVIEW_MOST_LIKE"})
	private OrderBy orderBy;

	public void rebaseToZero() {
		if (offset > 1) {
			offset--;
		}
	}
}
