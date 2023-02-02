package com.superboard.onbrd.global.entity;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;

import com.querydsl.core.types.OrderSpecifier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderBy {
	BOARDGAME_MOST_FAVORITE("보드게임_관심회원순", new OrderSpecifier<?>[] {boardgame.favoriteCount.desc()});

	private final String name;
	private final OrderSpecifier<?>[] orderSpecifiers;
}
