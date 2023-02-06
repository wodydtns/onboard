package com.superboard.onbrd.review.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ReviewByBoardgameIdResponse {
	private Boolean hasNext;
	private List<ReviewCard> reviews;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ReviewCard {
		private Long id;
		private float grade;
		private String content;
		private List<String> images;
		private long likeCount;
		private LocalDateTime createdAt;
		private Long writerId;
		private String profileCharacter;
		private String nickname;
		private List<CommentCard> comments;
	}

	@Getter
	@NoArgsConstructor
	public static class CommentCard {
		private Long id;
		private String content;
		private LocalDateTime createdAt;
		private Long writerId;
		private String profileCharacter;
		private String nickname;
	}
}
