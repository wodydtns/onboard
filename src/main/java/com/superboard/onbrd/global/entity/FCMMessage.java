package com.superboard.onbrd.global.entity;

import lombok.*;

@Getter
@Builder
public class FCMMessage {
    String registrationToken;
    String title;
    String message;
    String eventType;
    String boardgameId;

}
