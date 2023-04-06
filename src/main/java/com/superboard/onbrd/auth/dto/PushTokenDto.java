package com.superboard.onbrd.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PushTokenDto {

    private Long id;

    private String androidPushToken;

    private String applePushToken;
}

