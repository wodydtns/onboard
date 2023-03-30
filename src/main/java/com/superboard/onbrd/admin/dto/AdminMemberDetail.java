package com.superboard.onbrd.admin.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.superboard.onbrd.member.entity.MemberLevel;
import com.superboard.onbrd.member.entity.MemberStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminMemberDetail {
	private Long id;
	private String email;
	private String nickname;
	private String profileCharacter;
	private MemberLevel level;
	private int point;
	private MemberStatus status;
	private LocalDateTime signedUpAt;
	private LocalDateTime lastVisitAt;
	private List<ReviewDetail> reviews;
	private List<CommentDetail> comments;

	@Getter
	@NoArgsConstructor
	public static class ReviewDetail {
		private Long id;
		private String content;
		private LocalDateTime createdAt;
		private Long boardgameId;
		private String boardgameName;
	}

	@Getter
	@NoArgsConstructor
	public static class CommentDetail {
		private Long id;
		private String content;
		private LocalDateTime createdAt;
		private Long reviewId;
		private Long boardgameId;
		private String boardgameName;
	}
}
