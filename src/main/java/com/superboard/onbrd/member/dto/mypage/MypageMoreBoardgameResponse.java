package com.superboard.onbrd.member.dto.mypage;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MypageMoreBoardgameResponse {
	private Boolean hasNext;
	private List<BoardGameCard> favoriteBoardgames;

	public static class BoardGameCard {
		private Long id;
		private String image;
	}
}
