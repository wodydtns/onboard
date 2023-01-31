package com.superboard.onbrd.boardgame.dto;

import java.util.List;

import com.superboard.onbrd.global.entity.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardgameSearchByTagRequest {
	private List<Long> tagIds;
	private int offset;
	private int limit;
	private OrderBy orderBy;
}
