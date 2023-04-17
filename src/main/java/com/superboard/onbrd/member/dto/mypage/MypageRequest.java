package com.superboard.onbrd.member.dto.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MypageRequest {
	private int reviewCount;
	private int boardGameCount;
}
