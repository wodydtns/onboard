package com.superboard.onbrd.tag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.tag.dto.TagListResponse;
import com.superboard.onbrd.tag.service.TagService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Tag")
@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
	private final TagService tagService;

	@Tag(name = "Tag")
	@ApiOperation(value = "태그 목록 조회")
	@GetMapping
	public ResponseEntity<TagListResponse> getTagList() {
		TagListResponse response = tagService.getTagListGroupByType();

		return ResponseEntity.ok(response);
	}
}
