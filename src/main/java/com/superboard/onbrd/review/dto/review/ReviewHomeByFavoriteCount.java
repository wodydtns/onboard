package com.superboard.onbrd.review.dto.review;

import java.util.List;

import javax.persistence.Convert;

import com.querydsl.core.annotations.QueryProjection;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse.BoardGameResponse;
import com.superboard.onbrd.global.converter.ImagesJsonConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ReviewHomeByFavoriteCount {
	
	private Boolean hasNext;
	private List<BoardGameResponse> boardGameResponses;
	
	@Getter
	@NoArgsConstructor
	@Setter
	public static class  ReviewHomeByFavoriteCountResponse {
		private Long id;
		private String image;
		private String content;
		private String writer;
		private String level;
		private String title;
		private long likeCount;
	}

}
