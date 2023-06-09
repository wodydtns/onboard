package com.superboard.onbrd.notification.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.superboard.onbrd.global.converter.NotificationTypeConverter;
import com.superboard.onbrd.member.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@Convert(converter = NotificationTypeConverter.class)
	private NotificationType type;
	@Column(nullable = false)
	private String payload;
	@Column(nullable = false)
	private boolean isChecked = false;
	@Column(nullable = false)
	private LocalDateTime publishedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member receiver;

	public void check() {
		this.isChecked = true;
	}
}
