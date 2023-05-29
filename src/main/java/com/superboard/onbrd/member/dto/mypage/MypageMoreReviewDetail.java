package com.superboard.onbrd.member.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MypageMoreReviewDetail {
	private Long id;
	private Long boardGameId;
	private String boardGameName;
	private String boardGameImage;
}
