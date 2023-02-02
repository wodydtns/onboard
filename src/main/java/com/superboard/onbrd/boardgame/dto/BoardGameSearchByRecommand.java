package com.superboard.onbrd.boardgame.dto;

import javax.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardGameSearchByRecommand {
	
	@Column(table = "TAG",name = "name")
	private String tagName;
	
	@Column(table = "TAG",name = "type")
	private String type;
	
}
