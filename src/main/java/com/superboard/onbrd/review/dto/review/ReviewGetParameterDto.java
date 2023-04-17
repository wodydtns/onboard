package com.superboard.onbrd.review.dto.review;

import com.superboard.onbrd.global.entity.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewGetParameterDto {
	private Long boardGameId;
	private long offset;
	private int limit;
	private OrderBy orderBy;

	public static ReviewGetParameterDto of(Long boardGameId, ReviewGetRequest request) {
		ReviewGetParameterDto dto = new ReviewGetParameterDto();
		dto.boardGameId = boardGameId;
		dto.offset = request.getOffset();
		dto.limit = request.getLimit();
		dto.orderBy = request.getOrderBy();

		return dto;
	}
}
