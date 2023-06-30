package com.superboard.onbrd.review.dto.review;

import com.superboard.onbrd.member.entity.MemberLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewByFavoriteCountDetail {
	private Long id;
	private String content;
	private float grade;
	private String writerNickname;
	private MemberLevel writerLevel;
	private Long boardGameId;
	private String boardGameTitle;
	private String boardgameImage;
	private long likeCount;
	private String profileCharacter;
}
