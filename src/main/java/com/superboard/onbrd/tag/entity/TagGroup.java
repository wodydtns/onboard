package com.superboard.onbrd.tag.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TagGroup {
	PLAYER_COUNT("인원"),
	GENRE("장르"),
	DIFFICULTY("난이도"),
	MECHANISM("메커니즘"),
	CATEGORY("카테고리");
	private final String name;
}
