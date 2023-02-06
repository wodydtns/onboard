package com.superboard.onbrd.member.dto.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MypageMoreRequest {
	private long offset;
	private int limit;
}
