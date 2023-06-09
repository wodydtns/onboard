package com.superboard.onbrd.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository {
}
