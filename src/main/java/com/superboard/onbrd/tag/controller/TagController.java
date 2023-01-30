package com.superboard.onbrd.tag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.tag.dto.TagListResponse;
import com.superboard.onbrd.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
	private final TagService tagService;

	@GetMapping
	public ResponseEntity<TagListResponse> getTagList() {
		TagListResponse response = tagService.getTagListGroupByType();

		return ResponseEntity.ok(response);
	}
}
