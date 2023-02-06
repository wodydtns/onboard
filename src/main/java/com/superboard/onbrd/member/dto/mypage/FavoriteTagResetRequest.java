package com.superboard.onbrd.member.dto.mypage;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteTagResetRequest {
	private List<Long> tagIds;
}
