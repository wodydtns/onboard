package com.superboard.onbrd.review.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.global.converter.ImagesJsonConverter;
import com.superboard.onbrd.global.entity.BaseEntity;
import com.superboard.onbrd.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private float grade;
	@Column(nullable = false)
	private String content;
	@Column
	@Convert(converter = ImagesJsonConverter.class)
	private List<String> images;
	@Column(nullable = false)
	private long likeCount = 0;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private Member writer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boardgame_id")
	private Boardgame boardgame;

	public void updateGrade(float grade) {
		this.grade = grade;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateImages(List<String> images) {
		this.images = images;
	}

	public void increaseLikeCountOrDecreaseIfCancelLike(boolean isCancelLike) {
		if (isCancelLike) {
			this.likeCount--;
			return;
		}

		this.likeCount++;
	}

	@Builder
	private Review(float grade, String content, List<String> images, Member writer, Boardgame boardgame) {
		this.grade = grade;
		this.content = content;
		this.images = images;
		this.writer = writer;
		this.boardgame = boardgame;
	}
}
