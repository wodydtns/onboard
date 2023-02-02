package com.superboard.onbrd.tag.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.tag.entity.FavoriteTag;
import com.superboard.onbrd.tag.entity.Tag;
import com.superboard.onbrd.tag.repository.FavoriteTagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FavoriteTagServiceImpl implements FavoriteTagService {
	private final FavoriteTagRepository favoriteTagRepository;
	private final TagService tagService;
	private final MemberService memberService;

	@Override
	public void createdFavoriteTags(Member member, List<Long> tagIds) {
		List<FavoriteTag> toBeSaved = new ArrayList<>();
		addToBeSaved(toBeSaved, member, tagIds);

		favoriteTagRepository.saveAll(toBeSaved);
	}

	@Override
	public void resetFavoriteTags(Long memberId, List<Long> tagIds) {
		List<FavoriteTag> toBeDeleted = new ArrayList<>();
		List<FavoriteTag> toBeSaved = new ArrayList<>();

		Member member = memberService.findVerifiedOneById(memberId);
		List<FavoriteTag> favoriteTags = favoriteTagRepository.findAllByMember(member);

		for (FavoriteTag favoriteTag : favoriteTags) {
			if (!tagIds.contains(favoriteTag.getTag().getId())) {
				toBeDeleted.add(favoriteTag);
				continue;
			}
			tagIds.remove(favoriteTag.getId());
		}

		addToBeSaved(toBeSaved, member, tagIds);

		favoriteTagRepository.saveAll(toBeSaved);
		favoriteTagRepository.deleteAll(toBeDeleted);
	}

	private void addToBeSaved(List<FavoriteTag> toBeSaved, Member member, List<Long> tagIds) {
		for (Long tagId : tagIds) {
			Optional<Tag> tagOrNull = tagService.findById(tagId);
			if (tagOrNull.isEmpty()) {
				log.error("TAG_NOT_FOUND");
				continue;
			}
			toBeSaved.add(FavoriteTag.of(member, tagOrNull.get()));
		}
	}
}
