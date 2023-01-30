package com.superboard.onbrd.boardgame.entity;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
public class BoardGameSearchByRecommand {

	@ApiModelProperty(example = "인원 ")
	private String playCount;
	
	@ApiModelProperty(example = "난이도 ")
	private String difficulty;
	
	// tag name
	@ApiModelProperty(example = "태그이름 ")
	private String name;
}
