package com.superboard.onbrd.member.dto.mypage;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class MypageMoreReviewResponse {
	private Boolean hasNext;
	private List<ReviewCard> myReviews;

	@Getter
	@NoArgsConstructor
	public static class ReviewCard {
		private Long id;
		private List<String> images;
	}
}
