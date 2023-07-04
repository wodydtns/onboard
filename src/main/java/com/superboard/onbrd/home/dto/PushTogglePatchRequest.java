package com.superboard.onbrd.home.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Getter
@NoArgsConstructor
public class PushTogglePatchRequest {

    private String commentYn;

    private String favoriteTagYn;
}
