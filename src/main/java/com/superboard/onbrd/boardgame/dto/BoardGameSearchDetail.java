package com.superboard.onbrd.boardgame.dto;

import com.superboard.onbrd.tag.entity.Tag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardGameSearchDetail {
	@ApiModelProperty(notes = "boardgame PK")
	private Long id;
	@ApiModelProperty(notes = "boardgame 이름")
	private String name;
	@ApiModelProperty(notes = "boardgame 이미지")
	private String image;

	private float grade;

	private List<Tag> tagList = new ArrayList<>();
}
