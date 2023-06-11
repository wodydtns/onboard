package com.superboard.onbrd.review.dto.review;

import java.util.List;

import com.superboard.onbrd.member.entity.MemberLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewByFavoriteCountDetail {
	private Long id;
	private List<String> images;
	private String content;
	private String writerNickname;
	private MemberLevel writerLevel;
	private String boardGameTitle;
	private long likeCount;
}
