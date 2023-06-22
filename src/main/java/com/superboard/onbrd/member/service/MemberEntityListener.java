package com.superboard.onbrd.member.service;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.stereotype.Service;

import com.superboard.onbrd.global.util.BeanUtils;
import com.superboard.onbrd.member.entity.BadgeHistory;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.repository.BadgeHistoryRepository;

@Service
public class MemberEntityListener {

	@PostLoad
	public void postLoad(Member member) {
		member.loadPreBadges();
	}

	@PrePersist
	public void prePersist(Member member) {
		if (member.checkBadgeGain()) {
			saveBadgeHistory(member);
		}
	}

	@PreUpdate
	public void preUpdate(Member member) {
		if (member.checkBadgeGain()) {
			saveBadgeHistory(member);
		}
	}

	private void saveBadgeHistory(Member member) {
		BadgeHistory badgeHistory = BadgeHistory.from(member);

		BadgeHistoryRepository badgeHistoryRepository = BeanUtils.getBean(BadgeHistoryRepository.class);
		badgeHistoryRepository.save(badgeHistory);
	}
}
