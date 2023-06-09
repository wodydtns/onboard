package com.superboard.onbrd.review.dto.review;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewByBoardgameDetail {
	private Long id;
	private float grade;
	private String content;
	private List<String> images;
	private long likeCount;
	private Boolean isHidden;
	private LocalDateTime createdAt;
	private Long writerId;
	private String profileCharacter;
	private String nickname;
	private Boolean isLiked;
}
