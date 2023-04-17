package com.superboard.onbrd.boardgame.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardgameSearchDetail {
	@ApiModelProperty(notes = "boardgame PK")
	private Long id;
	@ApiModelProperty(notes = "boardgame 이름")
	private String name;
	@ApiModelProperty(notes = "boardgame 이미지")
	private String image;
}
