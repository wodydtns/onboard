package com.superboard.onbrd.member.dto.mypage;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class MypageMoreBoardgameResponse {
	private Boolean hasNext;
	private List<BoardGameCard> favoriteBoardgames;

	@Getter
	@NoArgsConstructor
	public static class BoardGameCard {
		private Long id;
		private String image;
	}
}
