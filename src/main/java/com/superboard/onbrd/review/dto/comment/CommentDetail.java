package com.superboard.onbrd.review.dto.comment;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDetail {
	private Long id;
	private String content;
	private Boolean isHidden;
	private LocalDateTime createdAt;
	private Long writerId;
	private String profileCharacter;
	private String nickname;
}
