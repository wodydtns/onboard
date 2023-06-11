package com.superboard.onbrd.review.dto.review;

import com.superboard.onbrd.member.entity.MemberLevel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewByFavoriteCountDetail {
	private Long id;
	private String images;
	private String content;
	private String writerNickname;
	private MemberLevel writerLevel;
	private String boardGameTitle;
	private long likeCount;
}
