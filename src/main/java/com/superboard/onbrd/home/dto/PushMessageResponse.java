package com.superboard.onbrd.home.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PushMessageResponse {

    private String targetToken;
    private String title;
    private String body;

    private String notificationId;

    private String eventType;

    private String boardgameId;

    private long writerId;

}
