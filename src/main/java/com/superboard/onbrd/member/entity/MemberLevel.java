package com.superboard.onbrd.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberLevel {
	JOKER(5000),
	SPADE(1000),
	DIAMOND(500),
	HEART(200),
	CLOVER(100),
	PLAYER(0);

	private final int lowerPoint;

	public static MemberLevel getLevelCorrespondingPoint(int point) {
		MemberLevel[] values = MemberLevel.values();

		for (MemberLevel value : values) {
			if (point >= value.lowerPoint) {
				return value;
			}
		}

		return PLAYER;
	}
}
