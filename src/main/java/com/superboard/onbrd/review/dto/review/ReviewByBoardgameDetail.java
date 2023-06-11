package com.superboard.onbrd.review.dto.review;

import java.time.LocalDateTime;
import java.util.List;

import com.superboard.onbrd.member.entity.MemberLevel;

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
	private long commentCount;
	private LocalDateTime createdAt;
	private Long writerId;
	private String profileCharacter;
	private String nickname;
	private MemberLevel writerLevel;
	private Boolean isLiked;

	public void setCommentCount(Long count) {
		if (count == null) {
			commentCount = 0;
			return;
		}

		this.commentCount = count;
	}
}
