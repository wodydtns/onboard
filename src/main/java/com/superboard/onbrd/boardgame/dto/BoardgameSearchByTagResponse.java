package com.superboard.onbrd.boardgame.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
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
		@ApiModelProperty(notes = "boardgame PK")
		private Long id;
		@ApiModelProperty(notes = "boardgame 이름")
		private String name;
		@ApiModelProperty(notes = "boardgame 이미지")
		private String image;
	}
}
