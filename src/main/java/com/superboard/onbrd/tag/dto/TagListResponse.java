package com.superboard.onbrd.tag.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TagListResponse {
	private List<TypeTagMap> typeTagMaps;

	@AllArgsConstructor
	public static class TypeTagMap {
		private String type;
		private List<TagResponse> tags;
	}

	@Getter
	@AllArgsConstructor
	public static class TagResponse {
		private Long id;
		private String name;
	}
}
