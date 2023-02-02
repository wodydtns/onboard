package com.superboard.onbrd.tag.service;

import java.util.Optional;

import com.superboard.onbrd.tag.dto.TagListResponse;
import com.superboard.onbrd.tag.entity.Tag;

public interface TagService {
	Optional<Tag> findById(Long id);

	TagListResponse getTagListGroupByType();
}
