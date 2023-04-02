package com.superboard.onbrd.boardgame.dto;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;
import com.superboard.onbrd.tag.entity.Tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "보드게임 상세")
public class BoardgameDetailDto {

	@ApiModelProperty(notes = "보드게임 상세 아이디", example = "1")
	private Long id;
	@ApiModelProperty(notes = "보드게임 이름", example = "Bramlisim")
	private String name;
	@ApiModelProperty(notes = "보드게임 설명", example = "Bramlisim is ....")

	private String description;
	@ApiModelProperty(notes = "보드게임 이미지", example = "Bramlisim.png")
	private String image;
	@ApiModelProperty(notes = "보드게임 좋아요 수", example = "1")
	private long favoriteCount = 0;

	@ApiModelProperty(notes = "보드게임 태그 리스트", example = "신화")
	private List<Tag> tagList = new ArrayList<>();

	@QueryProjection
	public BoardgameDetailDto(Long id,String name, String description, String image, long favoriteCount) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.favoriteCount = favoriteCount;
	}
	
}
