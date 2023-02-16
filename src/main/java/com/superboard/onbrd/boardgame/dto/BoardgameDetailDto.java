package com.superboard.onbrd.boardgame.dto;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;
import com.superboard.onbrd.tag.entity.Tag;

import lombok.Data;

@Data
public class BoardgameDetailDto {
	
	private String name;

	private String description;

	private String image;

	private long favoriteCount = 0;

	private List<Tag> tagList = new ArrayList<>();

	@QueryProjection
	public BoardgameDetailDto(String name, String description, String image, long favoriteCount) {
		this.name = name;
		this.description = description;
		this.image = image;
		this.favoriteCount = favoriteCount;
	}
	
}
