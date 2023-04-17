package com.superboard.onbrd.member.dto.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MypageGetDto {
	private String email;
	private int reviewCount;
	private int boardGameCount;

	public static MypageGetDto of(String email, MypageRequest request) {
		MypageGetDto dto = new MypageGetDto();
		dto.email = email;
		dto.reviewCount = request.getReviewCount();
		dto.boardGameCount = request.getBoardGameCount();

		return dto;
	}
}
