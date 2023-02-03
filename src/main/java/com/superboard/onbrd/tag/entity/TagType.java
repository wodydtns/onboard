package com.superboard.onbrd.tag.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagType {
	PLAYER_COUNT("인원"),
	GENRE("장르"),
	MECHANISM("메커니즘"),
	CATEGORY("카테고리"),
	TYPE("타입");
	private final String desc;
}
