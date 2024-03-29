package com.superboard.onbrd.boardgame.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Valid
@ApiModel(description = "보드게임 검색 시 request 파라미터")
public class BoardgameSearchByTagRequest {

	@NotEmpty
	@ApiModelProperty(notes = "태그들(파라미터)", example = "1,2,3,4,5")
	private List<Long> tagIds;
	@ApiModelProperty(notes = "페이지 시작", example = "1")
	private int offset;
	@ApiModelProperty(notes = "페이지 끝", example = "10")
	private int limit;
	@ApiModelProperty(notes = "보드게임 이름", example = "brims")
	private String name;

	public void rebaseToZero() {
		if (offset > 0) {
			offset--;
		}
	}
}
