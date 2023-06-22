package com.superboard.onbrd.member.entity;

import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.superboard.onbrd.global.converter.BadgeSortedSetConverter;
import com.superboard.onbrd.global.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BadgeHistory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	@Convert(converter = BadgeSortedSetConverter.class)
	private SortedSet<Badge> preBadges;
	@Column(nullable = false)
	@Convert(converter = BadgeSortedSetConverter.class)
	private SortedSet<Badge> currentBadges;
	@Column(name = "checked_yn", nullable = false)
	private boolean isChecked;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private BadgeHistory(Member member) {
		this.preBadges = member.getPreBadges();
		this.currentBadges = member.getBadges();
		this.isChecked = false;
		this.member = member;
	}

	public static BadgeHistory from(Member member) {
		return new BadgeHistory(member);
	}
}
