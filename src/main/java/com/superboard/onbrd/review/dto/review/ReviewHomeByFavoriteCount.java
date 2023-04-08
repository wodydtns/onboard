package com.superboard.onbrd.review.dto.review;

import java.util.List;

import com.superboard.onbrd.boardgame.dto.BoardgameSearchDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ReviewHomeByFavoriteCount {

	private Boolean hasNext;
	private List<BoardgameSearchDetail> boardGameResponses;

	@Getter
	@NoArgsConstructor
	@Setter
	public static class ReviewHomeByFavoriteCountResponse {
		private Long id;
		private String image;
		private String content;
		private String writer;
		private String level;
		private String title;
		private long likeCount;
	}

}
