package com.superboard.onbrd.notification.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.superboard.onbrd.global.converter.NotificationTypeConverter;
import com.superboard.onbrd.home.dto.PushMessageResponse;
import com.superboard.onbrd.member.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	@Convert(converter = NotificationTypeConverter.class)
	private NotificationType notificationType;
	// eventType, boardgameId -> json을 string으로
	@Column(nullable = false)
	private String payload;
	@Column(nullable = false)
	private boolean isChecked = false;
	@Column(nullable = false)
	private LocalDateTime pushedAt = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private Member receiver;

	public void check() {
		this.isChecked = true;
	}

	public void gainnotificationType(NotificationType notificationType){
		this.notificationType = notificationType;
	}

	public static Notification from(Member member,NotificationType notificationType, PushMessageResponse payload ){
		Notification notification = new Notification();
		notification.notificationType = notificationType;
		notification.receiver = member;
		notification.payload = String.format("{\"eventType\":\"%s\", \"boardgameId\":\"%s\",  \"writerId\":\"%s\"}", notificationType, payload.getBoardgameId(), payload.getWriterId());
		return notification;
	}
}
