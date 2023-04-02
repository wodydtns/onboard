package com.superboard.onbrd.boardgame.dto;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "top 10 보드게임들")
public class TopBoardgameDto {
	private Long id;
	
	private String name;
	
	@QueryProjection
	public TopBoardgameDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
