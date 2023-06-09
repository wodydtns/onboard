package com.superboard.onbrd.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.notification.entity.Notification;

public interface NotificationRepository
	extends JpaRepository<Notification, Long>, CustomNotificationRepository {
}
