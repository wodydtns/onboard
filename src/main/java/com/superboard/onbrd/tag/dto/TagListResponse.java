package com.superboard.onbrd.tag.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagListResponse {
	private List<TypeTagMap> typeTagMaps;

	@Getter
	@AllArgsConstructor
	public static class TypeTagMap {
		@Schema(description = "태그 타입", example = "카테고리")
		private String type;
		private List<TagResponse> tags;
	}

	@Getter
	@AllArgsConstructor
	public static class TagResponse {
		@Schema(description = "태그 ID", example = "1")
		private Long id;
		@Schema(description = "태그 이름", example = "전쟁")
		private String name;
	}
}
