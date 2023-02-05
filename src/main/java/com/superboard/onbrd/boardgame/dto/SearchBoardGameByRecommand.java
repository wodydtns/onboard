package com.superboard.onbrd.boardgame.dto;

import javax.persistence.Column;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardGameSearchByRecommand {
	
	@Column(table = "TAG",name = "name")
	private String tagName;
	
	@Column(table = "TAG",name = "type")
	private String type;
	
	@QueryProjection
	public BoardGameSearchByRecommand(String tagName,String type) {
		this.tagName =tagName;
		this.type= type;
	}
}
