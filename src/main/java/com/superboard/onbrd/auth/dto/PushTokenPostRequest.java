package com.superboard.onbrd.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PushTokenPostRequest {

    private Long memberId;

    private String pushTokenValue;

}

