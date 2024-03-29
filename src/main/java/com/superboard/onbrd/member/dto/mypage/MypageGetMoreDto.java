package com.superboard.onbrd.member.dto.mypage;

import com.superboard.onbrd.global.dto.OnbrdSliceRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MypageGetMoreDto {
	private String email;
	private long offset;
	private int limit;

	public static MypageGetMoreDto of(String email, OnbrdSliceRequest request) {
		MypageGetMoreDto dto = new MypageGetMoreDto();
		dto.email = email;
		dto.offset = request.getOffset();
		dto.limit = request.getLimit();

		return dto;
	}
}
