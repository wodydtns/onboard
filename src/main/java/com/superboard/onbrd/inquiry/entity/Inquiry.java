package com.superboard.onbrd.inquiry.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.superboard.onbrd.global.entity.BaseEntity;
import com.superboard.onbrd.inquiry.dto.InquiryCreateCommand;
import com.superboard.onbrd.member.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	private Boolean isAnswered = false;
	@Column
	private String answer;
	@Column
	private String adminEmail;
	@Column
	private LocalDateTime answeredAt;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void recieveAnswer(String answer, String adminEmail) {
		this.answer = answer;
		this.adminEmail = adminEmail;
	}

	public static Inquiry of(Member member, InquiryCreateCommand command) {
		Inquiry inquiry = new Inquiry();

		inquiry.title = command.getTitle();
		inquiry.content = command.getContent();
		inquiry.member = member;

		return inquiry;
	}
}
