package com.superboard.onbrd.review.dto.review;

import java.util.List;

import javax.persistence.Convert;

import com.querydsl.core.annotations.QueryProjection;
import com.superboard.onbrd.global.converter.ImagesJsonConverter;

import lombok.Data;

@Data
public class ReviewHomeByFavoriteCount {

	private Long id;
	
	private long likeCount = 0;
	
	private List<String> images;
	
	private String nickname;

	@QueryProjection
	public ReviewHomeByFavoriteCount(Long id, long likeCount, List<String> images, String nickname) {
		this.id = id;
		this.likeCount = likeCount;
		this.images = images;
		this.nickname = nickname;
	}
}
