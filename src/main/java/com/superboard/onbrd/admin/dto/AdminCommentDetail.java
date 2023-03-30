package com.superboard.onbrd.admin.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminCommentDetail {
	private Long id;
	private String content;
	private Boolean isHidden;
	private LocalDateTime createdAt;
	private Long writerId;
	private String nickname;
	private Long reviewId;
	private Long boardgameId;
	private String boardgameName;
}
