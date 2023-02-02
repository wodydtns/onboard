package com.superboard.onbrd.tag.service;

import java.util.List;

import com.superboard.onbrd.member.entity.Member;

public interface FavoriteTagService {
	void createdFavoriteTags(Member member, List<Long> tagIds);

	void resetFavoriteTags(Long memberId, List<Long> tagIds);
}
