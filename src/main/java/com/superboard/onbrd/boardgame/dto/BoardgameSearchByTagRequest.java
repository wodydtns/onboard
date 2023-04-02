package com.superboard.onbrd.boardgame.dto;

import java.util.List;

import com.superboard.onbrd.global.entity.OrderBy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "보드게임 검색 시 request 파라미터")
public class BoardgameSearchByTagRequest {

	@ApiModelProperty(notes = "태그들(파라미터)",example = "1,2,3,4,5")
	private List<Long> tagIds;
	private int offset;
	private int limit;
	@ApiModelProperty(notes = "태그들(파라미터)",example = "1,2,3,4,5")
	private OrderBy orderBy;
}
