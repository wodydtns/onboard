package com.superboard.onbrd.boardgame.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class BoardgameSearchByTagResponse {
	private Boolean hasNext;
	private List<BoardGameResponse> boardGameResponses;

	@Getter
	@NoArgsConstructor
	public static class BoardGameResponse {
		private Long id;
		private String name;
		private String image;
	}
}
