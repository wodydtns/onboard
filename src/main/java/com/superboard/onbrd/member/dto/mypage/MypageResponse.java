package com.superboard.onbrd.member.dto.mypage;

import java.util.List;

import com.superboard.onbrd.member.entity.MemberLevel;
import com.superboard.onbrd.tag.entity.TagType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MypageResponse {
	private String nickname;
	private String profileCharacter;
	private MemberLevel level;
	private int point;
	private List<TagCard> favoriteTags;
	private List<ReviewCard> myReviews;
	private List<BoardgameCard> favoriteBoardgames;

	@Getter
	@NoArgsConstructor
	public static class TagCard {
		private Long id;
		private String name;
		private TagType type;
	}

	@Getter
	@NoArgsConstructor
	public static class ReviewCard {
		private Long id;
		private Long boardGameId;
		private String boardGameName;
		private String boardGameImage;
	}

	@Getter
	@NoArgsConstructor
	public static class BoardgameCard {
		private Long id;
		private String name;
		private String image;
	}
}
