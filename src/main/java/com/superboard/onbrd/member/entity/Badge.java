package com.superboard.onbrd.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Badge {
	JOIN("처음이에요!"),
	POST_FIRST_REVIEW("시작이반이다"),
	POST_FIVE_REVIEWS("온더보드 홀릭"),
	ATTEND_SEVEN_DAYS("프로출석러"),
	ATTEND_THIRTY_DAYS("고인물"),
	SET_PROFILE_CHARACTER("오늘부터 1일"),
	SET_PUSH_ALARM_ON("놓치지 않을거에요"),
	POST_FIVE_COMMENTS("무플방위원회"),
	SELECTED_RECOMMENDED_REVIEW("보드게전도사"),
	GAIN_TEN_REVIEW_LIKES("취향 전도사");

	private final String phrase;
}

