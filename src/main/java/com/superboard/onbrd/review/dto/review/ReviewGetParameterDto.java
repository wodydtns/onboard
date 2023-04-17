package com.superboard.onbrd.review.dto.review;

import com.superboard.onbrd.global.entity.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewGetParameterDto {
	private Long boardgameId;
	private long offset;
	private int limit;
	private OrderBy orderBy;

	public static ReviewGetParameterDto of(Long boardgameId, ReviewGetRequest request) {
		ReviewGetParameterDto dto = new ReviewGetParameterDto();
		dto.boardgameId = boardgameId;
		dto.offset = request.getOffset();
		dto.limit = request.getLimit();
		dto.orderBy = request.getOrderBy();

		return dto;
	}
}
