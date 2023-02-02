package com.superboard.onbrd.tag.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.tag.dto.TagListResponse;
import com.superboard.onbrd.tag.entity.Tag;
import com.superboard.onbrd.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
	private final TagRepository tagRepository;

	@Override
	public Optional<Tag> findById(Long id) {

		return tagRepository.findById(id);
	}

	@Override
	public TagListResponse getTagListGroupByType() {
		List<Tag> tags = tagRepository.findAll();
		Map<String, List<Tag>> typeTagListMap = tags.stream()
			.collect(Collectors.groupingBy(tag -> tag.getType().getName()));

		List<TagListResponse.TypeTagMap> typeTagMaps = new ArrayList<>();

		for (String key : typeTagListMap.keySet()) {
			List<TagListResponse.TagResponse> tagResponseList = typeTagListMap.get(key).stream()
				.map(tag -> new TagListResponse.TagResponse(tag.getId(), tag.getName()))
				.collect(Collectors.toList());
			TagListResponse.TypeTagMap typeTagMap = new TagListResponse.TypeTagMap(key, tagResponseList);
			typeTagMaps.add(typeTagMap);
		}

		return new TagListResponse(typeTagMaps);
	}
}
