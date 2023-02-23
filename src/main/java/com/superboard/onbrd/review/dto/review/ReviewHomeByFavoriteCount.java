package com.superboard.onbrd.review.dto.review;

import javax.persistence.Convert;

import com.querydsl.core.annotations.QueryProjection;
import com.superboard.onbrd.global.converter.ImagesJsonConverter;

import lombok.Data;

@Data
public class ReviewHomeByFavoriteCount {

	private Long id;
	
	private long likeCount = 0;
	
	@Convert(converter = ImagesJsonConverter.class)
	private String image;
	
	private String writer;

	@QueryProjection
	public ReviewHomeByFavoriteCount(Long id, long likeCount, String image, String writer) {
		this.id = id;
		this.likeCount = likeCount;
		this.image = image;
		this.writer = writer;
	}
}
