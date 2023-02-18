package com.superboard.onbrd.boardgame.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class BoardgameSearchByTagResponse {
	private Boolean hasNext;
	private List<BoardGameResponse> boardGameResponses;

	@Getter
	@NoArgsConstructor
	@Setter
	public static class BoardGameResponse {
		private Long id;
		private String name;
		private String image;
	}
}
