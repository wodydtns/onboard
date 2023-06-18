package com.superboard.onbrd.boardgame.dto;

import com.querydsl.core.annotations.QueryProjection;

import com.superboard.onbrd.tag.entity.Tag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "top 10 보드게임들")
public class TopBoardgameDto {
	private Long id;
	
	private String name;

	private String image;

	private float grade;

	private List<Tag> tagList = new ArrayList<>();
	
	@QueryProjection
	public TopBoardgameDto(Long id, String name, String image) {
		this.id = id;
		this.name = name;
		this.image=image;
	}
}
