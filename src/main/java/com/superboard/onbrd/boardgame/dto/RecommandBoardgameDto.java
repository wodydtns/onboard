package com.superboard.onbrd.boardgame.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommandBoardgameDto {
	private Long id;
	
	private String name;
	
	private String image;

	@QueryProjection
	public RecommandBoardgameDto(Long id, String name, String image) {
		this.id = id;
		this.name = name;
		this.image = image;
	}
}
