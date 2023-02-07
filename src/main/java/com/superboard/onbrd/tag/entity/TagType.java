package com.superboard.onbrd.tag.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagType {
	BEST_PLAYER("권장 인원"),
	PLAYTIME("게임 시간"),
	AGE("권장 연령"),
	CATEGORY("카테고리");

	private final String desc;
}
