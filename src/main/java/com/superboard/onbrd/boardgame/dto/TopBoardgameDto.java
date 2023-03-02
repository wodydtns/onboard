package com.superboard.onbrd.boardgame.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TopBoardgameDto {
	private Long id;
	
	private String name;
	
	@QueryProjection
	public TopBoardgameDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
