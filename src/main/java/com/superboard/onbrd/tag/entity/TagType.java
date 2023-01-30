package com.superboard.onbrd.tag.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagType {
	PLAYER_COUNT("인원"),
	GENRE("장르"),
	DIFFICULTY("난이도"),
	MECHANISM("메커니즘"),
	CATEGORY("카테고리");
	private final String name;
}
